/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
    id("android-base-convention")
    id("dev.icerock.mobile.multiplatform.android-manifest")
}

kotlin {
    android {
        publishAllLibraryVariants()
        publishLibraryVariantsGroupedByFlavor = true
    }
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting
        val commonTest by getting
        val iosMain by creating
        val iosTest by creating

        iosMain.dependsOn(commonMain)
        iosTest.dependsOn(commonTest)

        val iosTargets = listOf("iosArm64", "iosX64", "iosSimulatorArm64")

        iosTargets.map { "${it}Main" }.forEach { getByName(it).dependsOn(iosMain) }
        iosTargets.map { "${it}Test" }.forEach { getByName(it).dependsOn(iosTest) }

        val mobileMain by creating
        val mobileTest by creating
        val androidMain by getting
        val androidTest by getting

        mobileMain.dependsOn(commonMain)
        mobileTest.dependsOn(commonTest)

        androidMain.dependsOn(mobileMain)
        androidTest.dependsOn(mobileTest)

        iosMain.dependsOn(mobileMain)
        iosTest.dependsOn(mobileTest)

        all {
            languageSettings {
                optIn("kotlin.RequiresOptIn")
            }
        }
    }
}

tasks.withType<AbstractTestTask> {
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events = setOf(
            TestLogEvent.SKIPPED,
            TestLogEvent.PASSED,
            TestLogEvent.FAILED
        )
        showStandardStreams = true
    }
    outputs.upToDateWhen { false }
}
