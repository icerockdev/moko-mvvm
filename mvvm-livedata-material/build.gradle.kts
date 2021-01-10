/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    plugin(Deps.Plugins.androidLibrary)
    plugin(Deps.Plugins.kotlinAndroid)
    plugin(Deps.Plugins.mavenPublish)
}

group = "dev.icerock.moko"
version = Deps.mokoMvvmVersion

android {
    sourceSets.all { java.srcDir("src/$name/kotlin") }
}

dependencies {
    implementation(Deps.Libs.MultiPlatform.coroutines)

    api(project(":mvvm-livedata"))

    api(Deps.Libs.MultiPlatform.mokoResources.common)

    api(Deps.Libs.Android.appCompat)
    api(Deps.Libs.Android.lifecycle)
    api(Deps.Libs.Android.material)
    api(Deps.Libs.Android.coroutines)
}

afterEvaluate {
    publishing.publications {
        create("release", MavenPublication::class.java) {
            from(components.getByName("release"))
        }
    }
}
