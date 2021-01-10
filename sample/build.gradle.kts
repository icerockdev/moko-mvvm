/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

subprojects {
    configurations.all {
        resolutionStrategy.dependencySubstitution {
            listOf(
                Deps.Libs.MultiPlatform.mokoMvvm to ":mvvm",
                Deps.Libs.MultiPlatform.mokoMvvmCore to ":mvvm-core",
                Deps.Libs.MultiPlatform.mokoMvvmLiveData to ":mvvm-livedata",
                Deps.Libs.MultiPlatform.mokoMvvmLiveDataMaterial to ":mvvm-livedata-material",
                Deps.Libs.MultiPlatform.mokoMvvmLiveDataGlide to ":mvvm-livedata-glide",
                Deps.Libs.MultiPlatform.mokoMvvmLiveDataSwipeRefresh to ":mvvm-livedata-swiperefresh",
                Deps.Libs.MultiPlatform.mokoMvvmDataBinding to ":mvvm-databinding",
                Deps.Libs.MultiPlatform.mokoMvvmViewBinding to ":mvvm-viewbinding",
                Deps.Libs.MultiPlatform.mokoMvvmState to ":mvvm-state",
                Deps.Libs.MultiPlatform.mokoMvvmStateDeprecated to ":mvvm-state-deprecated",
                Deps.Libs.MultiPlatform.mokoMvvmTest to ":mvvm-test"
            ).forEach { (module, path) ->
                substitute(module(module)).with(project(path))
            }
        }
    }
}
