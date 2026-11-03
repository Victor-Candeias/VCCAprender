import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

// Independent APK: release must be signed with the same key as VCCAprender.
val pluginVersion = providers.gradleProperty("pluginVersion").orElse("0.1.0").get()
val pluginBuildNumber = providers.gradleProperty("pluginBuildNumber")
    .orElse(providers.environmentVariable("GITHUB_RUN_NUMBER"))
    .orElse("1").get().toInt()
require(Regex("[0-9]+\\.[0-9]+\\.[0-9]+").matches(pluginVersion))
require(pluginBuildNumber in 1..2100000000)

val localKeys = Properties().apply {
    val f = rootProject.file("keystore.properties")
    if (f.isFile) f.reader(Charsets.UTF_8).use { load(it) }
}
fun signingValue(env: String, key: String): String? =
    providers.environmentVariable(env).orNull?.takeIf { it.isNotBlank() }
        ?: localKeys.getProperty(key)?.takeIf { it.isNotBlank() }

val releaseKeyStore = rootProject.file(
    signingValue("VCC_RELEASE_STORE_FILE", "storeFile")
        ?: "${System.getProperty("user.home")}/.keystores/VCCProjects.jks"
)
val releaseStorePassword = signingValue("VCC_RELEASE_STORE_PASSWORD", "storePassword")
val releaseKeyAlias = signingValue("VCC_RELEASE_KEY_ALIAS", "keyAlias") ?: "vccprojects"
val releaseKeyPassword = signingValue("VCC_RELEASE_KEY_PASSWORD", "keyPassword") ?: releaseStorePassword

android {
    namespace = "com.vcc.aprender.samplegame"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.vcc.aprender.samplegame"
        minSdk = 26
        targetSdk = 35
        versionCode = pluginBuildNumber
        versionName = pluginVersion
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
    signingConfigs {
        create("release") {
            storeFile = releaseKeyStore
            storeType = "PKCS12"
            storePassword = releaseStorePassword
            keyAlias = releaseKeyAlias
            keyPassword = releaseKeyPassword
        }
    }
    buildTypes {
        getByName("release") { signingConfig = signingConfigs.getByName("release") }
    }
}

tasks.matching { it.name == "preReleaseBuild" }.configureEach {
    doFirst {
        require(releaseKeyStore.isFile) { "Keystore Release não encontrada." }
        require(!releaseStorePassword.isNullOrBlank() && !releaseKeyPassword.isNullOrBlank()) {
            "Configura os GitHub Secrets ou keystore.properties local para o plugin Release."
        }
    }
}

dependencies {
    implementation(project(":game-plugin-sdk"))
}
