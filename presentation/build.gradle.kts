plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id(BuildPlugins.HILT)
    kotlin("kapt")
}

android {
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    //Core
    implementation(Deps.Core.DEPENDENCY)

    //Compose
    implementation(Deps.Compose.RUNTIME)

    //Coroutines
    implementation(Deps.Coroutines.DEP)

    //Test
    testImplementation(Deps.JUnit.TEST)

    //Hilt
    implementation(Deps.Hilt.HILT)
    kapt(Deps.Hilt.KAPT)

    //Modules
    implementation(project(Deps.Modules.DOMAIN))
    implementation(project(Deps.Modules.CONTRACT))
}

kapt {
    correctErrorTypes = true
}