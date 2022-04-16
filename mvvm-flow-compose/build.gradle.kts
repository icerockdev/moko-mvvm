/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("detekt-convention")
    id("org.jetbrains.compose")
    id("javadoc-stub-convention")
    id("publication-convention")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

kotlin {
    jvm()
    js(IR) {
        browser()
    }

    sourceSets {
        commonMain {
            dependencies {
                api(projects.mvvmFlow)

                api(compose.runtime)
            }
        }
    }
}
