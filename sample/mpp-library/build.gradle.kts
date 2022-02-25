plugins {
    id("com.android.library")
    id("android-base-convention")
    id("detekt-convention")
    id("org.jetbrains.kotlin.multiplatform")
    id("dev.icerock.mobile.multiplatform.android-manifest")
    id("dev.icerock.mobile.multiplatform.ios-framework")
    id("dev.icerock.moko.kswift")
}

kotlin {
    android()
    ios()
    iosSimulatorArm64()

    sourceSets {
        val iosMain by getting
        val iosSimulatorArm64Main by getting

        iosSimulatorArm64Main.dependsOn(iosMain)
    }
}

dependencies {
    commonMainImplementation(libs.coroutines)

    commonMainApi(libs.mokoResources)
    commonMainApi(projects.mvvmTest)
    commonMainApi(projects.mvvmLivedata)
    commonMainApi(projects.mvvmLivedataResources)
    commonMainApi(projects.mvvmCore)

    "androidMainApi"(projects.mvvmLivedataGlide)
    "androidMainApi"(projects.mvvmLivedataMaterial)
    "androidMainApi"(projects.mvvmLivedataSwiperefresh)

    commonTestImplementation(projects.mvvmTest)
    commonTestImplementation(libs.mokoTest)
}

framework {
    export(projects.mvvmCore)
    export(projects.mvvmLivedata)
    export(projects.mvvmLivedataResources)
    export(libs.mokoResources)
}

kswift {
    install(dev.icerock.moko.kswift.plugin.feature.PlatformExtensionFunctionsFeature)
    install(dev.icerock.moko.kswift.plugin.feature.SealedToSwiftEnumFeature)
}
