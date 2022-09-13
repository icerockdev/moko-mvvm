/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("kmm-library-convention")
    id("detekt-convention")
    id("publication-convention")
}

dependencies {
    commonMainApi(projects.mvvmFlow)
    commonMainApi(libs.mokoResources)
}
