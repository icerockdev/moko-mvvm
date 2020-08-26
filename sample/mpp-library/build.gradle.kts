plugins {
    plugin(Deps.Plugins.androidLibrary)
    plugin(Deps.Plugins.kotlinMultiplatform)
    plugin(Deps.Plugins.mobileMultiplatform)
    plugin(Deps.Plugins.iosFramework)
}

dependencies {
    commonMainImplementation(Deps.Libs.MultiPlatform.coroutines)

    androidMainImplementation(Deps.Libs.Android.lifecycle)

    commonMainApi(Deps.Libs.MultiPlatform.mokoResources.common)
    commonMainApi(Deps.Libs.MultiPlatform.mokoMvvm)
}

framework {
    export(project(":mvvm"))
    export(Deps.Libs.MultiPlatform.mokoResources)
}
