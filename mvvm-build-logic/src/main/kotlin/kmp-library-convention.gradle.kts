/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("kmm-library-convention")
}

kotlin {
    // JVM
    jvm()
    // JS
    js(IR) {
        browser()
        nodejs()
    }
    // linux
    linuxX64()
    // macOS
    macosArm64()
    macosX64()
    // watchOS
    watchosX64()
    watchosX86()
    watchosArm32()
    watchosArm64()
    watchosSimulatorArm64()
    // tvOS
    tvosArm64()
    tvosSimulatorArm64()
    tvosX64()
    // windows
    mingwX64()

    sourceSets {
        val nativeTargets = listOf(
            "iosArm32",
            "iosArm64",
            "iosX64",
            "iosSimulatorArm64",
            "macosArm64",
            "macosX64",
            "watchosX64",
            "watchosX86",
            "watchosArm32",
            "watchosArm64",
            "watchosSimulatorArm64",
            "tvosArm64",
            "tvosSimulatorArm64",
            "tvosX64",
            "linuxX64",
            "mingwX64"
        )
        val targetWithoutAndroid = nativeTargets + listOf(
            "js",
            "jvm",
        )

        // main
        val commonMain by getting
        val nonAndroidMain by creating
        nonAndroidMain.dependsOn(commonMain)
        val nativeMain by creating
        nativeMain.dependsOn(nonAndroidMain)

        targetWithoutAndroid.mapNotNull { findByName("${it}Main") }
            .forEach { it.dependsOn(nonAndroidMain) }

        nativeTargets.mapNotNull { findByName("${it}Main") }
            .forEach { it.dependsOn(nativeMain) }

        // test
        val commonTest by getting
        val nonAndroidTest by creating
        nonAndroidTest.dependsOn(commonTest)
        val nativeTest by creating
        nativeTest.dependsOn(nonAndroidTest)

        targetWithoutAndroid.mapNotNull { findByName("${it}Test") }
            .forEach { it.dependsOn(nonAndroidTest) }

        nativeTargets.mapNotNull { findByName("${it}Test") }
            .forEach { it.dependsOn(nativeTest) }

        commonMain.dependencies {
            implementation(kotlin("stdlib"))
        }
    }
}
