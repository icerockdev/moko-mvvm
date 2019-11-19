object Versions {
    object Android {
        const val compileSdk = 28
        const val targetSdk = 28
        const val minSdk = 16
    }

    const val kotlin = "1.3.60"

    object Libs {
        object Android {
            const val appCompat = "1.1.0"
            const val material = "1.0.0"
            const val lifecycle = "2.0.0"
        }

        object MultiPlatform {
            const val coroutines = "1.3.2-1.3.60-eap-76"
            const val mokoResources: String = "0.5.0"
            const val mokoMvvm: String = "0.4.0-dev-2"
        }
    }
}