/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()

        jcenter {
            content {
                includeGroup("org.jetbrains.trove4j")
                includeModule("org.jetbrains.kotlinx", "kotlinx-html-jvm")
            }
        }
    }
}

include(":mvvm-internal")
include(":mvvm-core")
include(":mvvm-livedata")
include(":mvvm-livedata-material")
include(":mvvm-livedata-glide")
include(":mvvm-livedata-swiperefresh")
include(":mvvm-databinding")
include(":mvvm-viewbinding")
include(":mvvm-state")
include(":mvvm-state-deprecated")
include(":mvvm-test")
include(":mvvm")
include(":sample:android-app")
include(":sample:mpp-library")
