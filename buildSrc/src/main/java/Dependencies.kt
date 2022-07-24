/**
 * To define plugins
 */
object BuildPlugins {
    val android by lazy { "com.android.tools.build:gradle:${Versions.gradlePlugin}" }
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}" }
}

/**
 * To define dependencies
 */
object Deps {
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}" }

    object Compose {

        val dependencies by lazy {
            listOf(
                "androidx.compose.ui:ui:1.3.0-alpha01",
                "androidx.compose.material3:material3:1.0.0-alpha14",
                "androidx.compose.ui:ui-tooling-preview:1.1.1",
                "androidx.activity:activity-compose:1.5.0"
            )
        }

        val debugDependencies by lazy {
            listOf(
                "androidx.compose.ui:ui-tooling:1.3.0-alpha01",
                "androidx.compose.ui:ui-test-manifest:1.3.0-alpha01"
            )
        }

        val androidTestDependencies by lazy {
            listOf(
                "androidx.compose.ui:ui-test-junit4:1.3.0-alpha01"
            )
        }
    }

    object Room {
        private const val room_version = "2.4.2"

        val dependencies by lazy {
            listOf("androidx.room:room-runtime:$room_version")
        }

        val annotationDependencies by lazy {
            listOf("androidx.room:room-compiler:$room_version")
        }
    }

    object Test {
        val testDependencies by lazy {
            listOf(
                "junit:junit:${Versions.jUnit}"
            )
        }

        val androidTestDependencies by lazy {
            listOf(
                "androidx.test.ext:junit:1.1.3",
                "androidx.test.espresso:espresso-core:3.4.0"
            )
        }
    }
}
