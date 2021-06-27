/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("multiplatform-library-convention")
    id("detekt-convention")
    id("publication-convention")
}

dependencies {
    // by default use old named state
    // core and livedata loaded transitive from it. not add directly - mergeDex will fail on android
    commonMainApi(projects.mvvmStateDeprecated)
    "androidMainApi"(projects.mvvmDatabinding)
}
