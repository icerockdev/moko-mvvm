plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
    id("dev.icerock.mobile.multiplatform")
    id("dev.icerock.mobile.multiplatform.ios-framework")
}

dependencies {
    commonMainImplementation(libs.coroutines)

    commonMainApi(libs.mokoResources)
    commonMainApi(projects.mvvm)
    commonMainApi(projects.mvvmTest)
    commonMainApi(projects.mvvmLivedata)
    commonMainApi(projects.mvvmCore)

    androidMainApi(projects.mvvmLivedataGlide)
    androidMainApi(projects.mvvmLivedataMaterial)
    androidMainApi(projects.mvvmLivedataSwiperefresh)

    commonTestImplementation(projects.mvvmTest)
    commonTestImplementation(libs.mokoMvvmTest)
}

framework {
    export(project(":mvvm-core"))
    export(project(":mvvm-livedata"))
    export(libs.mokoResources)
}
