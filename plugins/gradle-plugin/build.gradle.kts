/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("jvm-publication-convention")
    id("kotlin-kapt")

    id("com.github.gmazzo.buildconfig") version ("3.0.2")
    id("com.gradle.plugin-publish") version "0.16.0"
    id("java-gradle-plugin")
}

dependencies {
    implementation(gradleApi())
    implementation(gradleKotlinDsl())
    compileOnly(libs.kotlinGradlePlugin)
    implementation(libs.kotlinGradlePluginApi)

    compileOnly(libs.autoService)
    kapt(libs.autoService)
}

buildConfig {
    sourceSets.getByName("main") {
        buildConfig {
            buildConfigField("String", "compilerPluginVersion", "\"${project.version}\"")
        }
    }
}

gradlePlugin {
    plugins {
        create("moko-mvvm-flow-generics") {
            id = "dev.icerock.moko.mvvm.flow-generics"
            implementationClass = "dev.icerock.moko.mvvm.gradle.FlowAccessorGeneratorGradleSubplugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/icerockdev/moko-mvvm"
    vcsUrl = "https://github.com/icerockdev/moko-mvvm"
    description = "Plugin for codegen Flow with generics on Kotlin/Native"
    tags = listOf("moko-mvvm", "moko", "kotlin", "kotlin-multiplatform")

    plugins {
        getByName("moko-mvvm-flow-generics") {
            displayName = "MOKO mvvm Flow generics plugin"
        }
    }

    mavenCoordinates {
        groupId = project.group as String
        artifactId = project.name
        version = project.version as String
    }
}
