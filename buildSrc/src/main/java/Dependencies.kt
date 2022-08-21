/**
 * To define plugins
 */

private const val KOTLIN_VERSION = "1.5.0"

object BuildPlugins {
    private const val GRADLE_PLUGIN_VERSION = "4.2.1"

    const val ANDROID = "com.android.tools.build:gradle:$GRADLE_PLUGIN_VERSION"
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION"
    const val HILT = "com.google.dagger.hilt.android"
}

/**
 * To define dependencies
 */
object Deps {
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$KOTLIN_VERSION"

    object Core {
        const val DEPENDENCY = "androidx.core:core-ktx:1.8.0"
    }

    object Coroutines {
        const val DEP = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"
    }

    object Material {
        const val DEPENDENCY = "com.google.android.material:material:1.6.1"
    }

    object Compose {
        private const val COMPOSE_VERSION = "1.2.1"

        const val UI = "androidx.compose.ui:ui:$COMPOSE_VERSION"
        const val MATERIAL3 = "androidx.compose.material3:material3:1.0.0-alpha16"
        const val PREVIEW = "androidx.compose.ui:ui-tooling-preview:1.2.1"
        const val CONSTRAINT = "androidx.constraintlayout:constraintlayout-compose:1.0.1"
        const val ACTIVITY = "androidx.activity:activity-compose:1.5.1"
        const val RUNTIME = "androidx.compose.runtime:runtime:$COMPOSE_VERSION"
        const val FOUNDATION = "androidx.compose.foundation:foundation:$COMPOSE_VERSION"
        const val VIEW_MODEL = "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1"
        const val DEBUG_TOOLING = "androidx.compose.ui:ui-tooling:$COMPOSE_VERSION"
        const val DEBUG_MANIFEST = "androidx.compose.ui:ui-test-manifest:$COMPOSE_VERSION"
        const val ANDROID_UI_TEST = "androidx.compose.ui:ui-test-junit4:$COMPOSE_VERSION"
    }

    object Room {
        private const val ROOM_VERSION = "2.4.3"

        const val RUNTIME = "androidx.room:room-runtime:$ROOM_VERSION"
        const val KTX = "androidx.room:room-ktx:$ROOM_VERSION"
        const val KAPT_COMPILER = "androidx.room:room-compiler:$ROOM_VERSION"
    }

    object JUnit {
        const val TEST = "junit:junit:4.13.2"
        const val ANDROID_TEST = "androidx.test.ext:junit:1.1.3"
    }

    object Mockk {
        const val TEST = "io.mockk:mockk:1.12.5"
    }

    object Truth {
        const val TEST = "com.google.truth:truth:1.1.3"
    }

    object Espresso {
        const val ANDROID_TEST = "androidx.test.espresso:espresso-core:3.4.0"
    }

    object Navigation {
        const val NAV_COMPOSE = "androidx.navigation:navigation-compose:2.5.1"
    }

    object Hilt {
        private const val HILT_VERSION = "2.43.2"
        private const val HILT_OTHER_JETPACK_VERSION = "1.0.0"

        const val HILT = "com.google.dagger:hilt-android:$HILT_VERSION"
        const val KAPT = "com.google.dagger:hilt-android-compiler:$HILT_VERSION"
        const val WORKER = "androidx.hilt:hilt-work:$HILT_OTHER_JETPACK_VERSION"
        const val KAPT_WORKER = "androidx.hilt:hilt-compiler:$HILT_OTHER_JETPACK_VERSION"
        const val NAVIGATION_COMPOSE =
            "androidx.hilt:hilt-navigation-compose:$HILT_OTHER_JETPACK_VERSION"
    }

    object WorkManager {
        const val RUNTIME = "androidx.work:work-runtime-ktx:2.7.1"
    }

    object DataStore {
        private const val DATA_STORE_VERSION = "1.0.0"

        const val PREFS = "androidx.datastore:datastore-preferences-core:$DATA_STORE_VERSION"
        const val CORE = "androidx.datastore:datastore:$DATA_STORE_VERSION"

    }

    object Accompanies {
        const val DEPS = "com.google.accompanist:accompanist-permissions:0.26.0-alpha"
    }

    object Modules {
        const val PRESENTATION = ":presentation"
        const val DOMAIN = ":domain"
        const val API = ":api"
    }
}
