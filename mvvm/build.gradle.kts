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
    // by default use old named state
    // core and livedata loaded transitive from it. not add directly - mergeDex will fail on android
    commonMainApi(project(":mvvm-state-deprecated"))
    androidMainApi(project(":mvvm-databinding"))
}
