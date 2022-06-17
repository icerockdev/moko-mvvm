/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("kmp-library-convention")
    id("detekt-convention")
}

dependencies {
    commonMainImplementation(projects.mvvmInternal)

    commonMainApi(projects.mvvmStateFlow)
    commonMainApi(projects.mvvmStateLivedata)
    commonMainApi(libs.kotlinTestJUnit)
    commonMainApi(libs.mokoTest)
}
