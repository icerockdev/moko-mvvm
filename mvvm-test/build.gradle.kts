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
    commonMainApi(libs.coroutines)

    commonMainApi(projects.mvvmCore)
    commonMainImplementation(projects.mvvmInternal)

    commonMainApi(libs.kotlinTestJUnit)
    androidMainApi(libs.coroutinesTest)
    androidMainApi(libs.androidCoreTesting)
}
