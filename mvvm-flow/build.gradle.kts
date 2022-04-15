/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("kmp-library-convention")
    id("detekt-convention")
    id("publication-convention")
}

dependencies {
    commonMainImplementation(libs.mokoKSwift)
    commonMainApi(projects.mvvmCore)

    androidMainImplementation(libs.lifecycleKtx)

    commonTestApi(libs.mokoTest)
    commonTestApi(projects.mvvmTest)
}
