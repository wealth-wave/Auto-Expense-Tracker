plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id(BuildPlugins.HILT)
    kotlin("kapt")
}

apply {
    from("$rootDir/jacoco.gradle")
}

android {
    compileSdk = ConfigData.targetSdkVersion

    defaultConfig {
        applicationId = "app.expense.tracker"
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

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
        jvmTarget = ConfigData.jvmVersion
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = ConfigData.kotlinCompilerVersion
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Core
    implementation(Deps.Core.DEPENDENCY)

    // Compose
    implementation(Deps.Compose.UI)
    implementation(Deps.Compose.MATERIAL3)
    implementation(Deps.Compose.PREVIEW)
    implementation(Deps.Compose.ACTIVITY)
    implementation(Deps.Compose.RUNTIME)
    implementation(Deps.Compose.FOUNDATION)
    implementation(Deps.Compose.CONSTRAINT)
    implementation(Deps.Compose.VIEW_MODEL)
    implementation(Deps.Material.DEPENDENCY)
    debugImplementation(Deps.Compose.DEBUG_TOOLING)
    debugImplementation(Deps.Compose.DEBUG_MANIFEST)
    androidTestImplementation(Deps.Compose.ANDROID_UI_TEST)

    // Permission
    implementation(Deps.Accompanies.DEPS)

    // Navigation
    implementation(Deps.Navigation.NAV_COMPOSE)

    // WorkManager
    implementation(Deps.WorkManager.RUNTIME)

    // Coroutines
    implementation(Deps.Coroutines.DEP)

    // Hilt
    implementation(Deps.Hilt.HILT)
    kapt(Deps.Hilt.KAPT)
    implementation(Deps.Hilt.WORKER)
    kapt(Deps.Hilt.KAPT_WORKER)
    implementation(Deps.Hilt.NAVIGATION_COMPOSE)

    // Junit
    testImplementation(Deps.JUnit.TEST)
    androidTestImplementation(Deps.JUnit.ANDROID_TEST)

    // Espresso
    androidTestImplementation(Deps.Espresso.ANDROID_TEST)

    // Mock
    testImplementation(Deps.Mockk.TEST)

    // Truth
    testImplementation(Deps.Truth.TEST)

    // Modules
    implementation(project(Deps.Modules.PRESENTATION))
    implementation(project(Deps.Modules.DOMAIN))
    implementation(project(Deps.Modules.CONTRACT))
}

kapt {
    correctErrorTypes = true
}
