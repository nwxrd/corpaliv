plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    jacoco
}

extensions.configure<org.gradle.testing.jacoco.plugins.JacocoPluginExtension> {
    toolVersion = "0.8.14"
}

val corpalivApiBaseUrl = providers.gradleProperty("CORPALIV_API_BASE_URL")
    .orElse("http://10.0.2.2:8080/")

android {
    namespace = "cl.duoc.corpaliv_grupo9"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "cl.duoc.corpaliv_grupo9"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "API_BASE_URL", "\"${corpalivApiBaseUrl.get()}\"")

    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0-rc02")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("androidx.compose.material:material-icons-extended")
    debugImplementation(libs.androidx.compose.ui.tooling)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}

val appBuildDirectory = layout.buildDirectory.get().asFile
val coverageClasses = listOf(
    fileTree(appBuildDirectory.resolve("tmp/kotlin-classes/debug")) {
        include("cl/duoc/corpaliv_grupo9/viewmodel/**")
        include("cl/duoc/corpaliv_grupo9/repository/**")
        include("cl/duoc/corpaliv_grupo9/model/Carrito*")
        include("cl/duoc/corpaliv_grupo9/model/Producto*")
        include("cl/duoc/corpaliv_grupo9/model/Registro*")
        include("cl/duoc/corpaliv_grupo9/model/FormularioUiState*")
        include("cl/duoc/corpaliv_grupo9/data/model/**")
    },
    fileTree(appBuildDirectory.resolve("intermediates/javac/debug/compileDebugJavaWithJavac/classes")) {
        include("cl/duoc/corpaliv_grupo9/viewmodel/**")
        include("cl/duoc/corpaliv_grupo9/repository/**")
        include("cl/duoc/corpaliv_grupo9/model/Carrito*")
        include("cl/duoc/corpaliv_grupo9/model/Producto*")
        include("cl/duoc/corpaliv_grupo9/model/Registro*")
        include("cl/duoc/corpaliv_grupo9/model/FormularioUiState*")
        include("cl/duoc/corpaliv_grupo9/data/model/**")
    }
)

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")
    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }
    classDirectories.setFrom(files(coverageClasses))
    sourceDirectories.setFrom(files("src/main/java"))
    executionData.setFrom(fileTree(appBuildDirectory) {
        include("jacoco/testDebugUnitTest.exec")
    })
}

tasks.register<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
    dependsOn("testDebugUnitTest")
    classDirectories.setFrom(files(coverageClasses))
    sourceDirectories.setFrom(files("src/main/java"))
    executionData.setFrom(fileTree(appBuildDirectory) {
        include("jacoco/testDebugUnitTest.exec")
    })
    violationRules {
        rule {
            element = "BUNDLE"
            limit {
                counter = "INSTRUCTION"
                value = "COVEREDRATIO"
                minimum = "0.80".toBigDecimal()
            }
            limit {
                counter = "BRANCH"
                value = "COVEREDRATIO"
                minimum = "0.80".toBigDecimal()
            }
        }
    }
}

tasks.named("check") {
    dependsOn("jacocoTestCoverageVerification")
}