/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
    id("dev.icerock.mobile.multiplatform")
    id("org.gradle.maven-publish")
}

dependencies {
    commonMainImplementation(libs.coroutines)

    commonMainApi(projects.mvvmLivedata)

    androidMainApi(libs.appCompat)
    androidMainApi(libs.androidViewModel)

    androidMainImplementation(libs.coroutines)

    commonTestApi(libs.mokoTest)
    commonTestApi(projects.mvvmTest)
}
