/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("kmp-library-convention")
    id("detekt-convention")
    id("publication-convention")
}

dependencies {
    commonMainImplementation(projects.mvvmInternal)

    commonMainApi(libs.coroutines)

    androidMainApi(libs.lifecycleKtx)
    androidMainApi(libs.androidViewModel)
}
