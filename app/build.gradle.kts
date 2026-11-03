import com.android.build.api.variant.impl.VariantOutputImpl
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.devtools.ksp")
}
val appVersion = providers.gradleProperty("appVersion").orElse("0.1.0").get()
val buildNumber = providers.gradleProperty("buildNumber")
    .orElse(providers.environmentVariable("GITHUB_RUN_NUMBER"))
    .orElse("1").get().toInt()
require(buildNumber in 1..2100000000) { "buildNumber tem de estar entre 1 e 2100000000." }
require(Regex("[0-9]+\\.[0-9]+\\.[0-9]+").matches(appVersion)) {
    "appVersion deve usar o formato MAJOR.MINOR.PATCH."
}

// Credentials live in an ignored local file or environment variables (also usable in CI).
val releaseCredentials = Properties().apply {
    val credentialsFile = rootProject.file("keystore.properties")
    if (credentialsFile.isFile) credentialsFile.reader(Charsets.UTF_8).use { load(it) }
}
fun releaseSetting(environment: String, property: String): String? =
    providers.environmentVariable(environment).orNull?.takeIf { it.isNotBlank() }
        ?: releaseCredentials.getProperty(property)?.takeIf { it.isNotBlank() }

val releaseStoreFile = rootProject.file(
    releaseSetting("VCC_RELEASE_STORE_FILE", "storeFile")
        ?: "${System.getProperty("user.home")}/.keystores/VCCProjects.jks"
)
val releaseStorePassword = releaseSetting("VCC_RELEASE_STORE_PASSWORD", "storePassword")
val releaseKeyAlias = releaseSetting("VCC_RELEASE_KEY_ALIAS", "keyAlias") ?: "vccprojects"
val releaseKeyPassword = releaseSetting("VCC_RELEASE_KEY_PASSWORD", "keyPassword") ?: releaseStorePassword

android {
    namespace = "com.vcc.aprender"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.vcc.aprender"
        minSdk = 26
        targetSdk = 35
        versionCode = buildNumber
        versionName = appVersion
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
    buildFeatures { compose = true }
    signingConfigs {
        create("release") {
            storeFile = releaseStoreFile
            storeType = "PKCS12"
            storePassword = releaseStorePassword
            keyAlias = releaseKeyAlias
            keyPassword = releaseKeyPassword
        }
    }
    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
        }
    }
}

// A release must never silently fall back to an unsigned APK.
tasks.matching { it.name == "preReleaseBuild" }.configureEach {
    doFirst {
        require(releaseStoreFile.isFile) { "Keystore de release não encontrado: $releaseStoreFile" }
        require(!releaseStorePassword.isNullOrBlank() && !releaseKeyPassword.isNullOrBlank()) {
            "Configura storePassword em keystore.properties (ignorado pelo Git) ou VCC_RELEASE_STORE_PASSWORD. " +
                "Se a chave tiver outra password, configura também keyPassword ou VCC_RELEASE_KEY_PASSWORD."
        }
    }
}
dependencies {
    implementation(platform("androidx.compose:compose-bom:2024.12.01"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.12.01"))
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.navigation:navigation-compose:2.8.5")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test:core:1.6.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

androidComponents {
    onVariants(selector().withBuildType("release")) { variant ->
        variant.outputs.forEach { output ->
            (output as VariantOutputImpl).outputFileName =
                "VCCAprender-v${appVersion}-build${buildNumber}-release.apk"
        }
    }
}
