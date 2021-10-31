/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
    dependencies {
        classpath(":mvvm-build-logic")
    }
}

allprojects {
    group = "dev.icerock.moko.mvvm"
    version = rootProject.libs.versions.mokoMvvmVersion.get()

    project.plugins.withType<JavaPlugin> {
        project.configure<JavaPluginExtension> {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }
}
