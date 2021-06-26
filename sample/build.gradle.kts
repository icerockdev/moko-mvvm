/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

subprojects {
    configurations.all {
        resolutionStrategy.dependencySubstitution {
            listOf(
                "${libs.mokoMvvm.get().module.group}:${libs.mokoMvvm.get().module.name}:${libs.versions.mokoMvvmVersion.get()}" to ":mvvm",
                "${libs.mokoMvvmCore.get().module.group}:${libs.mokoMvvmCore.get().module.name}:${libs.versions.mokoMvvmVersion.get()}" to ":mvvm-core",
                "${libs.mokoMvvmLiveData.get().module.group}:${libs.mokoMvvmLiveData.get().module.name}:${libs.versions.mokoMvvmVersion.get()}" to ":mvvm-livedata",
                "${libs.mokoMvvmLiveDataMaterial.get().module.group}:${libs.mokoMvvmLiveDataMaterial.get().module.name}:${libs.versions.mokoMvvmVersion.get()}" to ":mvvm-livedata-material",
                "${libs.mokoMvvmLiveDataSwipeRefresh.get().module.group}:${libs.mokoMvvmLiveDataSwipeRefresh.get().module.name}:${libs.versions.mokoMvvmVersion.get()}" to ":mvvm-livedata-swiperefresh",
                "${libs.mokoMvvmDataBinding.get().module.group}:${libs.mokoMvvmDataBinding.get().module.name}:${libs.versions.mokoMvvmVersion.get()}" to ":mvvm-databinding",
                "${libs.mokoMvvmViewBinding.get().module.group}:${libs.mokoMvvmViewBinding.get().module.name}:${libs.versions.mokoMvvmVersion.get()}" to ":mvvm-viewbinding",
                "${libs.mokoMvvmState.get().module.group}:${libs.mokoMvvmState.get().module.name}:${libs.versions.mokoMvvmVersion.get()}" to ":mvvm-state",
                "${libs.mokoMvvmStateDeprecated.get().module.group}:${libs.mokoMvvmStateDeprecated.get().module.name}:${libs.versions.mokoMvvmVersion.get()}" to ":mvvm-state-deprecated",
                "${libs.mokoMvvmTest.get().module.group}:${libs.mokoMvvmTest.get().module.name}:${libs.versions.mokoMvvmVersion.get()}" to ":mvvm-test"
            ).forEach { (module, path) ->
                substitute(module(module)).with(project(path))
            }
        }
    }
}
