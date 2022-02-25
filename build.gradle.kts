/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()

        maven("https://jitpack.io")
    }

    dependencies {
        classpath(":mvvm-build-logic")
        classpath(libs.kswiftGradlePlugin)
    }
}

plugins {
    alias(libs.plugins.nexusPublish)
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            username.set(System.getenv("OSSRH_USER"))
            password.set(System.getenv("OSSRH_KEY"))
        }
    }
}

val mokoVersion = libs.versions.mokoMvvmVersion.get()
allprojects {
    group = "dev.icerock.moko"
    version = mokoVersion
}

// temporary fix for Apple Silicon (remove after 1.6.20 update)
rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>().nodeVersion = "16.0.0"
}
