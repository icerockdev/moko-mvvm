plugins {
    plugin(Deps.Plugins.androidLibrary)
    plugin(Deps.Plugins.kotlinMultiplatform)
    plugin(Deps.Plugins.mobileMultiplatform)
    plugin(Deps.Plugins.iosFramework)
}

dependencies {
    commonMainImplementation(Deps.Libs.MultiPlatform.coroutines)

    commonMainApi(Deps.Libs.MultiPlatform.mokoResources.common)
    commonMainApi(Deps.Libs.MultiPlatform.mokoMvvm)

    androidMainApi(Deps.Libs.MultiPlatform.mokoMvvmLiveDataGlide)
    androidMainApi(Deps.Libs.MultiPlatform.mokoMvvmLiveDataMaterial)
    androidMainApi(Deps.Libs.MultiPlatform.mokoMvvmLiveDataSwipeRefresh)
}

framework {
    export(project(":mvvm-core"))
    export(project(":mvvm-livedata"))
    export(Deps.Libs.MultiPlatform.mokoResources)
}
