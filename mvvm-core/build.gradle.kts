/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    plugin(Deps.Plugins.androidLibrary)
    plugin(Deps.Plugins.kotlinMultiplatform)
    plugin(Deps.Plugins.mobileMultiplatform)
    plugin(Deps.Plugins.mavenPublish)
}

group = "dev.icerock.moko"
version = Deps.mokoMvvmVersion

dependencies {
    commonMainImplementation(Deps.Libs.MultiPlatform.coroutines)

    androidMainApi(Deps.Libs.Android.appCompat)
    androidMainApi(Deps.Libs.Android.androidViewModel)

    androidMainImplementation(Deps.Libs.Android.coroutines)
}
