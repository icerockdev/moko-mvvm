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
    namespace = "dev.icerock.moko.mvvm.flow.compose"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(projects.mvvmFlow)

                api(compose.runtime)
            }
        }
    }
}
