object Deps {
    private const val kotlinVersion = "1.4.30"

    private const val androidAppCompatVersion = "1.2.0"
    private const val materialDesignVersion = "1.2.1"
    private const val androidLifecycleVersion = "2.2.0"
    private const val androidCoreTestingVersion = "2.1.0"
    private const val glideVersion = "4.11.0"
    private const val swipeRefreshVersion = "1.1.0"
    private const val mockitoVersion = "1.10.19"

    private const val detektVersion = "1.15.0"
    private const val dokkaVersion = "1.4.20"
    private const val bintrayPublishVersion = "0.1.0"

    private const val coroutinesVersion = "1.4.2"
    private const val mokoResourcesVersion = "0.15.0"
    private const val mokoTestVersion = "0.1.0"
    const val mokoMvvmVersion = "0.9.2"

    object Android {
        const val compileSdk = 29
        const val targetSdk = 29
        const val minSdk = 16
    }

    object Plugins {
        val androidApplication = GradlePlugin(id = "com.android.application")
        val androidLibrary = GradlePlugin(id = "com.android.library")
        val kotlinMultiplatform = GradlePlugin(id = "org.jetbrains.kotlin.multiplatform")
        val kotlinKapt = GradlePlugin(id = "kotlin-kapt")
        val kotlinAndroid = GradlePlugin(id = "kotlin-android")
        val mavenPublish = GradlePlugin(id = "org.gradle.maven-publish")

        val mobileMultiplatform = GradlePlugin(id = "dev.icerock.mobile.multiplatform")
        val iosFramework = GradlePlugin(id = "dev.icerock.mobile.multiplatform.ios-framework")

        val mokoResources = GradlePlugin(
            id = "dev.icerock.mobile.multiplatform-resources",
            module = "dev.icerock.moko:resources-generator:$mokoResourcesVersion"
        )

        val detekt = GradlePlugin(
            id = "io.gitlab.arturbosch.detekt",
            version = detektVersion
        )

        val dokka = GradlePlugin(
            id = "org.jetbrains.dokka",
            version = dokkaVersion
        )

        val bintrayPublish = GradlePlugin(
            id = "dev.icerock.gradle.bintray-publish",
            module = "dev.icerock.gradle:bintray-publish:$bintrayPublishVersion"
        )
    }

    object Libs {
        object Android {
            const val appCompat = "androidx.appcompat:appcompat:$androidAppCompatVersion"
            const val material = "com.google.android.material:material:$materialDesignVersion"
            const val lifecycle = "androidx.lifecycle:lifecycle-extensions:$androidLifecycleVersion"
            const val androidViewModel =
                "androidx.lifecycle:lifecycle-viewmodel-ktx:$androidLifecycleVersion"
            const val androidLiveData =
                "androidx.lifecycle:lifecycle-livedata-ktx:$androidLifecycleVersion"
            const val coroutines =
                "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
            const val glide =
                "com.github.bumptech.glide:glide:$glideVersion"
            const val swipeRefresh =
                "androidx.swiperefreshlayout:swiperefreshlayout:$swipeRefreshVersion"
        }

        object MultiPlatform {
            const val coroutines =
                "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
            val mokoResources = "dev.icerock.moko:resources:$mokoResourcesVersion"
                .defaultMPL(ios = true)
            const val mokoTest = "dev.icerock.moko:test:$mokoTestVersion"
            const val mokoMvvm = "dev.icerock.moko:mvvm:$mokoMvvmVersion"
            const val mokoMvvmCore = "dev.icerock.moko:mvvm-core:$mokoMvvmVersion"
            const val mokoMvvmLiveData = "dev.icerock.moko:mvvm-livedata:$mokoMvvmVersion"
            const val mokoMvvmLiveDataMaterial =
                "dev.icerock.moko:mvvm-livedata-material:$mokoMvvmVersion"
            const val mokoMvvmLiveDataGlide =
                "dev.icerock.moko:mvvm-livedata-glide:$mokoMvvmVersion"
            const val mokoMvvmLiveDataSwipeRefresh =
                "dev.icerock.moko:mvvm-livedata-swiperefresh:$mokoMvvmVersion"
            const val mokoMvvmDataBinding = "dev.icerock.moko:mvvm-databinding:$mokoMvvmVersion"
            const val mokoMvvmViewBinding = "dev.icerock.moko:mvvm-viewbinding:$mokoMvvmVersion"
            const val mokoMvvmState = "dev.icerock.moko:mvvm-state:$mokoMvvmVersion"
            const val mokoMvvmStateDeprecated =
                "dev.icerock.moko:mvvm-state-deprecated:$mokoMvvmVersion"
            const val mokoMvvmTest = "dev.icerock.moko:mvvm-test:$mokoMvvmVersion"
        }

        object Tests {
            const val kotlinTestJUnit =
                "org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion"
            const val androidCoreTesting =
                "androidx.arch.core:core-testing:$androidCoreTestingVersion"
            const val coroutinesTest =
                "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion"
            const val mockito =
                "org.mockito:mockito-core:$mockitoVersion"
        }

        object Jvm {
            const val detektFormatting =
                "io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion"
        }
    }
}
