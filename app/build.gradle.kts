plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.app.distribution)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.crashlytics.plugin)
}

val versionMajor = 0
val versionMinor = 0
val versionPatch = 0
val versionBuild = 1

fun generateVersionCode(): Int {
    return versionMajor * 10000 + versionMinor * 1000 + versionPatch * 10 + versionBuild
}

fun generateVersionName(): String {
    return "${versionMajor}.${versionMinor}.${versionPatch}.${versionBuild}"
}

android {
    namespace = "com.adewan.scout"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.adewan.scout"
        minSdk = 30
        targetSdk = 35
        versionCode = generateVersionCode()
        versionName = generateVersionName()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "clientId", "\"${System.getenv("IGDB_CLIENT_ID")}\"")
        buildConfigField("String", "clientSecret", "\"${System.getenv("IGDB_CLIENT_SECRET")}\"")
        buildConfigField(
            "String",
            "GOOGLE_OAUTH_CREDS",
            "\"${System.getenv("GOOGLE_OAUTH_CREDS")}\""
        )

        signingConfigs {
            getByName("debug") {
                // Set the values in your .bashrc/.zshrc file and
                // then restart android studio and gradle to get the values to pick up
                storeFile = file("$rootDir/dev.keystore")
                storePassword = System.getenv("SIGNING_STORE_PASSWORD")
                keyAlias = System.getenv("SIGNING_KEY_ALIAS")
                keyPassword = System.getenv("SIGNING_KEY_PASSWORD")
            }
        }


    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            versionNameSuffix = "-SNAPSHOT"
            firebaseAppDistribution {
                artifactType = "APK"
                groups = "dev-testers"
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }
    kotlinOptions {
        jvmTarget = "19"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
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

    //firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.crashlytics)


    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.firebase.firestore)

    //compose navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.koin.androidx.compose)

    //ktor
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.json)
    implementation(libs.ktor.client.content.negotiation)

    //json
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.logcat)
    implementation(libs.coil.compose)
    implementation(libs.compose.shimmer)
    implementation(libs.androidx.palette)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}