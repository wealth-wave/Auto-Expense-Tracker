plugins {
    id("com.android.application") version "7.2.1" apply false
    id("com.android.library") version "7.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.7.0" apply false
    id("org.jetbrains.kotlin.jvm") version "1.7.0" apply false
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.2")
        classpath(kotlin("gradle-plugin", version = "1.3.70"))
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.43.1")
    }
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint") // Version should be inherited from parent

    repositories {
        mavenCentral()
    }

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        debug.set(true)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}