/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("android-library-convention")
    id("detekt-convention")
    id("android-publication-convention")
    id("kotlin-kapt")
}

android {
    buildFeatures.dataBinding = true
}

dependencies {
    implementation(libs.coroutines)

    api(projects.mvvmLivedata)

    api(libs.mokoResources)

    api(libs.appCompat)
    api(libs.lifecycle)
    api(libs.material)
    api(libs.coroutines)

    // fix of package javax.annotation does not exist import javax.annotation.Generated in DataBinding code
    compileOnly("javax.annotation:jsr250-api:1.0")
}
