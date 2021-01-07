/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

pluginManagement {
    repositories {
        google()
        jcenter()

        maven { url = uri("https://dl.bintray.com/kotlin/kotlin") }
        maven { url = uri("https://kotlin.bintray.com/kotlinx") }
        maven { url = uri("https://dl.bintray.com/icerockdev/moko") }
    }
}

include(":mvvm-core")
include(":mvvm-livedata")
include(":mvvm-databinding")
include(":mvvm-viewbinding")
include(":mvvm-test")
include(":mvvm")
include(":sample:android-app")
include(":sample:mpp-library")
