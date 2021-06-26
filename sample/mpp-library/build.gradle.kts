plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
    id("dev.icerock.mobile.multiplatform")
    id("dev.icerock.mobile.multiplatform.ios-framework")
}

dependencies {
    commonMainImplementation(libs.coroutines)

    commonMainApi(libs.mokoResources)
    commonMainApi(libs.mokoMvvm)

    androidMainApi(libs.mokoMvvmLiveDataGlide)
    androidMainApi(libs.mokoMvvmLiveDataMaterial)
    androidMainApi(libs.mokoMvvmLiveDataSwipeRefresh)

    commonTestImplementation(libs.mokoTest)
    commonTestImplementation(libs.mokoMvvmTest)
}

framework {
    export(project(":mvvm-core"))
    export(project(":mvvm-livedata"))
    export(libs.mokoResources)
}
