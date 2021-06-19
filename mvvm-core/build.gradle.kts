/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    plugin(Deps.Plugins.androidLibrary)
    plugin(Deps.Plugins.kotlinMultiplatform)
    plugin(Deps.Plugins.mobileMultiplatform)
    plugin(Deps.Plugins.mavenPublish)
}

dependencies {
    commonMainImplementation(Deps.Libs.MultiPlatform.coroutines)

    commonMainImplementation(project(":mvvm-internal"))

    androidMainApi(Deps.Libs.Android.appCompat)
    androidMainApi(Deps.Libs.Android.androidViewModel)

    androidMainImplementation(Deps.Libs.Android.coroutines)
}

// see https://github.com/Kotlin/kotlinx-datetime/blob/978ae2f8c77253b16065f0c414708f74b2033e70/core/build.gradle.kts#L110
// as example
kotlin.targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
    if (konanTarget.family != org.jetbrains.kotlin.konan.target.Family.IOS) return@withType

    compilations["main"].cinterops {
        create("lifecycle") {
            val cinteropDir = "$projectDir/src/iosMain/cinterop"
            headers("$cinteropDir/headers/UIViewControllerLifecycle.h")
            defFile("$cinteropDir/lifecycle.def")
            extraOpts("-Xsource-compiler-option", "-I$cinteropDir/headers")
            extraOpts("-Xcompile-source", "$cinteropDir/sources/UIViewControllerLifecycle.m")
            extraOpts("-Xsource-compiler-option", "-fobjc-arc")
        }
    }
}
