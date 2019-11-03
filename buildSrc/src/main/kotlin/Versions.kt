object Versions {
    object Android {
        const val compileSdk = 28
        const val targetSdk = 28
        const val minSdk = 21
    }

    const val kotlin = "1.3.50"

    object Plugins {
        const val androidExtensions = Versions.kotlin
    }

    object Libs {
        object Android {
            const val appCompat = "1.0.2"
            const val material = "1.0.0"
            const val lifecycle = "2.0.0"
        }

        object MultiPlatform {
            const val coroutines = "1.3.0"
            const val mokoResources: String = "0.3.0"
            const val mokoMvvm: String = "0.3.1"
        }
    }
}