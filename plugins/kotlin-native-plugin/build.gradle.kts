/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("jvm-publication-convention")
    id("kotlin-kapt")
}

dependencies {
    compileOnly("org.jetbrains.kotlin:kotlin-compiler")

    compileOnly(libs.autoService)
    kapt(libs.autoService)
}
