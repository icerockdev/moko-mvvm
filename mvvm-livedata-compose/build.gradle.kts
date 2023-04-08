/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("kmp-library-convention")
    id("detekt-convention")
    id("org.jetbrains.compose")
    id("javadoc-stub-convention")
    id("publication-convention")
}

android {
    namespace = "dev.icerock.moko.mvvm.livedata.compose"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.mvvmLivedata)

                api(compose.runtime)
            }
        }
        val androidMain by getting {
            dependencies {
                api(libs.composeLiveData)
            }
        }
    }
}
