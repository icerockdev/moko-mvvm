/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("android-library-convention")
    id("detekt-convention")
    id("kotlin-kapt")
    id("android-publication-convention")
}

android {
    buildFeatures.viewBinding = true
}

dependencies {
    implementation(libs.coroutines)

    api(projects.mvvmCore)

    api(libs.mokoResources)

    api(libs.appCompat)
    api(libs.lifecycle)
    api(libs.coroutines)
}
