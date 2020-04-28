object Versions {
    object Android {
        const val compileSdk = 29
        const val targetSdk = 29
        const val minSdk = 16
    }

    const val kotlin = "1.3.70"

    object Libs {
        object Android {
            const val appCompat = "1.1.0"
            const val material = "1.0.0"
            const val lifecycle = "2.0.0"
        }

        object MultiPlatform {
            const val coroutines = "1.3.4"
            const val mokoResources: String = "0.9.0"
            const val mokoMvvm: String = "0.6.0"
        }
    }

    object Tests {
        const val androidCoreTesting = "2.1.0"
    }
}
