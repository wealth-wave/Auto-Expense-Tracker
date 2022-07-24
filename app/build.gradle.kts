plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = Versions.Sdk.targetSdkVersion

    defaultConfig {
        applicationId = "app.expense.tracker"
        minSdk = Versions.Sdk.minSdkVersion
        targetSdk = Versions.Sdk.targetSdkVersion
        versionCode = Versions.App.versionCode
        versionName = Versions.App.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        jvmTarget = Versions.jvm
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.kotlinCompilerVersion
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.8.0")
    Deps.Compose.dependencies.forEach {
        implementation(it)
    }
    Deps.Compose.androidTestDependencies.forEach {
        androidTestImplementation(it)
    }
    Deps.Compose.debugDependencies.forEach {
        debugImplementation(it)
    }
    Deps.Test.testDependencies.forEach {
        testImplementation(it)
    }
    Deps.Test.androidTestDependencies.forEach {
        androidTestImplementation(it)
    }
}