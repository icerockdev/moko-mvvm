/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("android-library-convention")
    id("detekt-convention")
    id("android-publication-convention")
}

dependencies {
    api(projects.mvvmLivedataResources)

    api(libs.material)
}
