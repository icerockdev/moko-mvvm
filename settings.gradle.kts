/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

pluginManagement {
    repositories {
        mavenCentral()
        google()

        maven { url = uri("https://dl.bintray.com/kotlin/kotlin") }
        maven { url = uri("https://kotlin.bintray.com/kotlinx") }
        maven { url = uri("https://dl.bintray.com/icerockdev/moko") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
}

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
