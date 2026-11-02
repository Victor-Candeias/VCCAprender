import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.Properties;

/** Reads credentials without printing them; emits only the public certificate fingerprint. */
class VerifyReleaseCertificate {
    static String setting(Properties props, String env, String key, String fallback) {
        String value = System.getenv(env);
        if (value == null || value.isBlank()) value = props.getProperty(key);
        return value == null || value.isBlank() ? fallback : value;
    }

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        try (var reader = Files.newBufferedReader(Path.of("keystore.properties"), StandardCharsets.UTF_8)) {
            props.load(reader);
        }
        String store = setting(props, "VCC_RELEASE_STORE_FILE", "storeFile",
            System.getProperty("user.home") + "/.keystores/VCCProjects.jks");
        String password = setting(props, "VCC_RELEASE_STORE_PASSWORD", "storePassword", null);
        String alias = setting(props, "VCC_RELEASE_KEY_ALIAS", "keyAlias", "vccprojects");
        if (password == null) throw new IllegalStateException("Password do keystore não configurada.");
        KeyStore keys = KeyStore.getInstance("PKCS12");
        try (var stream = Files.newInputStream(Path.of(store))) {
            keys.load(stream, password.toCharArray());
        }
        var certificate = keys.getCertificate(alias);
        if (certificate == null) throw new IllegalStateException("Certificado não encontrado para o alias.");
        byte[] digest = MessageDigest.getInstance("SHA-256").digest(certificate.getEncoded());
        System.out.println(HexFormat.of().formatHex(digest));
    }
}
