/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "moko-mvvm"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }
}

includeBuild("mvvm-build-logic")

include(":mvvm-internal")
include(":mvvm-core")
include(":mvvm-compose")
include(":mvvm-flow")
include(":mvvm-flow:apple")
include(":mvvm-flow-compose")
include(":mvvm-flow-resources")
include(":mvvm-flow-material")
include(":mvvm-livedata")
include(":mvvm-livedata-material")
include(":mvvm-livedata-resources")
include(":mvvm-livedata-glide")
include(":mvvm-livedata-swiperefresh")
include(":mvvm-livedata-compose")
include(":mvvm-databinding")
include(":mvvm-viewbinding")
include(":mvvm-state-core")
include(":mvvm-state-flow")
include(":mvvm-state-livedata")
include(":mvvm-test")
include(":sample:android-app")
include(":sample:mpp-library")
