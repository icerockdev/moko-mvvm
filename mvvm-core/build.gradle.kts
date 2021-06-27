/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("multiplatform-library-convention")
    id("detekt-convention")
    id("publication-convention")
}

dependencies {
    commonMainImplementation(libs.coroutines)

    commonMainImplementation(projects.mvvmInternal)

    "androidMainApi"(libs.appCompat)
    "androidMainApi"(libs.androidViewModel)
}
