plugins {
    id("com.android.library")
    id("android-base-convention")
    id("detekt-convention")
    id("org.jetbrains.kotlin.multiplatform")
    id("dev.icerock.mobile.multiplatform.android-manifest")
    id("dev.icerock.mobile.multiplatform.ios-framework")
}

kotlin {
    android()
    ios()
}

dependencies {
    commonMainImplementation(libs.coroutines)

    commonMainApi(libs.mokoResources)
    commonMainApi(projects.mvvm)
    commonMainApi(projects.mvvmTest)
    commonMainApi(projects.mvvmLivedata)
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
    export(libs.mokoResources)
}
