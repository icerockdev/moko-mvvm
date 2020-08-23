plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
    id("dev.icerock.mobile.multiplatform")
}

dependencies {
    commonMainImplementation(Deps.Libs.MultiPlatform.coroutines)

    androidMainImplementation(Deps.Libs.Android.lifecycle)

    commonMainApi(Deps.Libs.MultiPlatform.mokoResources)
    commonMainApi(Deps.Libs.MultiPlatform.mokoMvvm)
}
