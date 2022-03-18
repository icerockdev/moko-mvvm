/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("dev.icerock.moko.kswift")
}

val mokoResourcesVersion = "0.18.0"
val mokoMvvmVersion = "0.12.0"
val dependenciesList = listOf(
    "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt",
    "dev.icerock.moko:mvvm-core:$mokoMvvmVersion",
    "dev.icerock.moko:mvvm-livedata:$mokoMvvmVersion",
    "dev.icerock.moko:mvvm-livedata-resources:$mokoMvvmVersion",
    "dev.icerock.moko:resources:$mokoResourcesVersion"
)

kotlin {
    android()

    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { target ->
        target.binaries.framework {
            baseName = "shared"

            xcf.add(this)

            dependenciesList.forEach { export(it) }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                dependenciesList.forEach { api(it) }
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    compileSdk = 32
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }
}

afterEvaluate {
    val xcodeDir = File(project.buildDir, "xcode")

    tasks.filterIsInstance<org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFrameworkTask>()
        .forEach { xcFrameworkTask ->
            val syncName: String = xcFrameworkTask.name.replace("assemble", "sync")
            val xcframeworkDir =
                File(xcFrameworkTask.outputDir, xcFrameworkTask.buildType.getName())

            tasks.create(syncName, Sync::class) {
                this.group = "xcode"

                this.from(xcframeworkDir)
                this.into(xcodeDir)

                this.dependsOn(xcFrameworkTask)
            }
        }

    tasks.filterIsInstance<org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFrameworkTask>()
        .forEach { xcFrameworkTask ->
            val frameworkDir: File = xcFrameworkTask.frameworks.first().outputFile
            val swiftGenDir = File(frameworkDir.parent, frameworkDir.nameWithoutExtension + "Swift")
            val xcframeworkDir =
                File(xcFrameworkTask.outputDir, xcFrameworkTask.buildType.getName())
            val targetDir = File(xcframeworkDir, swiftGenDir.name)

            @Suppress("ObjectLiteralToLambda")
            xcFrameworkTask.doLast(object : Action<Task> {
                override fun execute(t: Task) {
                    targetDir.mkdirs()
                    swiftGenDir.copyRecursively(targetDir, overwrite = true)
                }
            })
        }
}

kswift {
    install(dev.icerock.moko.kswift.plugin.feature.SealedToSwiftEnumFeature)
    install(dev.icerock.moko.kswift.plugin.feature.PlatformExtensionFunctionsFeature)
}
