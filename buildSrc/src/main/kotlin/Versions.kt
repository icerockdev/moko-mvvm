object Versions {
    object Android {
        const val compileSdk = 29
        const val targetSdk = 29
        const val minSdk = 16
    }

    const val kotlin = "1.3.72"
    private const val androidArch = "2.1.0"

    object Libs {
        object Android {
            const val appCompat = "1.1.0"
            const val material = "1.0.0"
            const val lifecycle = androidArch
        }

        object MultiPlatform {
            const val coroutines = "1.3.7"
            const val mokoResources: String = "0.11.0"
            const val mokoMvvm: String = "0.7.0"
        }
    }

    object Tests {
        const val androidCoreTesting = androidArch
    }
}
