/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("android-base-convention")
}

android {
    sourceSets.all { java.srcDir("src/$name/kotlin") }
}
