/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import org.jetbrains.dokka.gradle.DokkaTaskPartial

buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    dependencies {
        classpath(":mvvm-build-logic")
        classpath(libs.kswiftGradlePlugin)
        classpath(libs.composeJetBrainsGradlePlugin)
        classpath(libs.dokkaGradlePlugin)
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

    configurations.configureEach {
        resolutionStrategy {
            force(rootProject.libs.coroutines)
        }
    }

    apply(plugin = "org.jetbrains.dokka")

    tasks.withType<DokkaTaskPartial>().configureEach {
        this.enabled = name.startsWith("mvvm-")
    }
}
