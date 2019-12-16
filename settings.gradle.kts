/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

pluginManagement {
    repositories {
        jcenter()
        google()

        maven { url = uri("https://dl.bintray.com/kotlin/kotlin") }
        maven { url = uri("https://kotlin.bintray.com/kotlinx") }
        maven { url = uri("https://jetbrains.bintray.com/kotlin-native-dependencies") }
        maven { url = uri("https://maven.fabric.io/public") }
    }
}

enableFeaturePreview("GRADLE_METADATA")

val properties = startParameter.projectProperties
// ./gradlew -PlibraryPublish :mvvm:publishToMavenLocal
val libraryPublish: Boolean = properties.containsKey("libraryPublish")

include(":mvvm")
if (!libraryPublish) {
    include(":sample:android-app")
    include(":sample:mpp-library")
}
