/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("kmp-library-convention")
    id("detekt-convention")
    id("publication-convention")
}

dependencies {
    commonMainApi(projects.mvvmLivedata)
    commonMainApi(projects.mvvmStateCore)

    commonTestApi(projects.mvvmTest)
    commonTestApi(projects.mvvmStateTest)
}
