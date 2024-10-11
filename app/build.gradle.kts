import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrainsKotlinKsp)
    alias(libs.plugins.hiltPlugin)
}

android {
    namespace = "com.jhonjto.android_bold"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jhonjto.android_bold"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
    }
    android {
        flavorDimensions += "env"
        productFlavors {
            create("development") {
                dimension = "env"
                buildConfigField("String", "API_URL", "\"https://api.weatherapi.com\"")
            }
            create("production") {
                dimension = "env"
                buildConfigField("String", "API_URL", "\"https://api.weatherapi.com\"")
            }
        }

        defaultConfig {
            val keystoreFile = project.rootProject.file("apikeys.properties")
            val properties = Properties()
            properties.load(keystoreFile.inputStream())

            //return empty key in case something goes wrong
            val apiKey = properties.getProperty("API_KEY") ?: ""

            buildConfigField(
                type = "String",
                name = "API_KEY",
                value = apiKey
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.viewmodel)
    implementation(libs.androidx.navigation)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    implementation(libs.coil)
    ksp(libs.dagger.compiler)
    ksp(libs.hilt.compiler)
    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.gson)
    implementation(libs.google.accompanist)
    implementation(platform(libs.okhttp3.bom))
    implementation(libs.okhttp)
    implementation(libs.okhttp.mockwebserver)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.core.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}