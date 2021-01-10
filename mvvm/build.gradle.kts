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
    // by default use old named state
    // core and livedata loaded transitive from it. not add directly - mergeDex will fail on android
    commonMainApi(project(":mvvm-state-deprecated"))
    androidMainApi(project(":mvvm-databinding"))
}
