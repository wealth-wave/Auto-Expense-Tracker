plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = ConfigData.targetSdkVersion

    defaultConfig {
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
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
        val options = this as org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
        options.jvmTarget = "1.8"
    }
}

dependencies {
    //Room
    implementation(Deps.Room.RUNTIME)
    implementation(Deps.Room.KTX)
    kapt(Deps.Room.KAPT_COMPILER)

    //Koin
    implementation(Deps.Koin.DEP_ANDROID)
    testImplementation(Deps.Koin.TEST)

    //Junit
    testImplementation(Deps.JUnit.TEST)
    androidTestImplementation(Deps.JUnit.ANDROID_TEST)

    //Modules
    implementation(project(Deps.Modules.CONTRACT))
}

kapt {
    correctErrorTypes = true
}