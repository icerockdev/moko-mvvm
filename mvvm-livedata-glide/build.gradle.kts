/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.gradle.maven-publish")
}

android {
    sourceSets.all { java.srcDir("src/$name/kotlin") }
}

dependencies {
    implementation(libs.coroutines)

    api(projects.mvvmLivedata)

    api(libs.mokoResources)

    api(libs.appCompat)
    api(libs.lifecycle)
    api(libs.glide)
    api(libs.coroutines)
}

afterEvaluate {
    publishing.publications {
        create("release", MavenPublication::class.java) {
            from(components.getByName("release"))
        }
    }
}
