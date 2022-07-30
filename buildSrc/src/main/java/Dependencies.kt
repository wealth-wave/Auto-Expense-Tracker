/**
 * To define plugins
 */

private const val KOTLIN_VERSION = "1.5.0"

object BuildPlugins {
    private const val GRADLE_PLUGIN_VERSION = "4.2.1"

    const val ANDROID = "com.android.tools.build:gradle:$GRADLE_PLUGIN_VERSION"
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION"
}

/**
 * To define dependencies
 */
object Deps {
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$KOTLIN_VERSION"

    object Core {
        const val DEPENDENCY = "androidx.core:core-ktx:1.8.0"
    }

    object Compose {
        private const val COMPOSE_VERSION = "1.3.0-alpha01"

        const val UI = "androidx.compose.ui:ui:$COMPOSE_VERSION"
        const val MATERIAL3 = "androidx.compose.material3:material3:1.0.0-alpha14"
        const val PREVIEW = "androidx.compose.ui:ui-tooling-preview:1.1.1"
        const val ACTIVITY = "androidx.activity:activity-compose:1.5.0"
        const val DEBUG_TOOLING = "androidx.compose.ui:ui-tooling:$COMPOSE_VERSION"
        const val DEBUG_MANIFEST = "androidx.compose.ui:ui-test-manifest:$COMPOSE_VERSION"
        const val ANDROID_UI_TEST = "androidx.compose.ui:ui-test-junit4:$COMPOSE_VERSION"
    }

    object Room {
        private const val ROOM_VERSION = "2.4.2"

        const val RUNTIME = "androidx.room:room-runtime:$ROOM_VERSION"
        const val ANNOTATION_COMPILER = "androidx.room:room-compiler:$ROOM_VERSION"
    }

    object JUnit {
        const val TEST = "junit:junit:4.13.2"
        const val ANDROID_TEST = "androidx.test.ext:junit:1.1.3"
    }

    object Mockk {
        const val TEST = "io.mockk:mockk:1.12.4"
    }

    object Truth {
        const val TEST = "com.google.truth:truth:1.1.3"
    }

    object Espresso {
        const val ANDROID_TEST = "androidx.test.espresso:espresso-core:3.4.0"
    }

    object Hilt {
        private const val HILT_VERSION = "2.38.1"

        const val HILT = "com.google.dagger:hilt-android:$HILT_VERSION"
        const val KAPT = "com.google.dagger:hilt-android-compiler:$HILT_VERSION"
    }

    object WorkManager {
        const val RUNTIME = "androidx.work:work-runtime-ktx:2.7.1"
    }

    object Modules {
        const val PRESENTATION = ":presentation"
        const val DOMAIN = ":domain"
        const val API = ":api"
        const val CONTRACT = ":contract"
    }
}
