/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import java.util.Base64

plugins {
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.5.0"
}

buildscript {
    repositories {
        mavenCentral()
        google()

        gradlePluginPortal()
    }

    dependencies {
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.15.0")
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:1.4.20")

        classpath("dev.icerock:mobile-multiplatform:0.11.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.20")
        classpath("com.android.tools.build:gradle:4.2.1")
    }
}

allprojects {

    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jetbrains.dokka")

    configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
        input.setFrom(
            "src/commonMain/kotlin",
            "src/androidMain/kotlin",
            "src/iosMain/kotlin",
            "src/main/kotlin"
        )
    }

    dependencies {
        "detektPlugins"(rootProject.libs.detektFormatting)
    }

    plugins.withId("com.android.library") {
        configure<com.android.build.gradle.LibraryExtension> {
            compileSdkVersion(libs.versions.compileSdk.get().toInt())

            defaultConfig {
                minSdkVersion(libs.versions.minSdk.get().toInt())
                targetSdkVersion(libs.versions.targetSdk.get().toInt())
            }
        }
    }

    plugins.withId("org.gradle.maven-publish") {
        group = "dev.icerock.moko"
        version = libs.versions.mokoMvvmVersion.get()

        val javadocJar by tasks.registering(Jar::class) {
            archiveClassifier.set("javadoc")
        }

        configure<PublishingExtension> {
            repositories.maven("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/") {
                name = "OSSRH"

                credentials {
                    username = System.getenv("OSSRH_USER")
                    password = System.getenv("OSSRH_KEY")
                }
            }

            publications.withType<MavenPublication> {
                // Stub javadoc.jar artifact
                artifact(javadocJar.get())

                // Provide artifacts information requited by Maven Central
                pom {
                    name.set("MOKO mvvm")
                    description.set("Model-View-ViewModel architecture components for mobile (android & ios) Kotlin Multiplatform development")
                    url.set("https://github.com/icerockdev/moko-mvvm")
                    licenses {
                        license {
                            url.set("https://github.com/icerockdev/moko-mvvm/blob/master/LICENSE.md")
                        }
                    }

                    developers {
                        developer {
                            id.set("Alex009")
                            name.set("Aleksey Mikhailov")
                            email.set("aleksey.mikhailov@icerockdev.com")
                        }
                    }

                    scm {
                        connection.set("scm:git:ssh://github.com/icerockdev/moko-mvvm.git")
                        developerConnection.set("scm:git:ssh://github.com/icerockdev/moko-mvvm.git")
                        url.set("https://github.com/icerockdev/moko-mvvm")
                    }
                }
            }

            apply(plugin = "signing")

            configure<SigningExtension> {
                val signingKeyId: String? = System.getenv("SIGNING_KEY_ID")
                val signingPassword: String? = System.getenv("SIGNING_PASSWORD")
                val signingKey: String? = System.getenv("SIGNING_KEY")?.let { base64Key ->
                    String(Base64.getDecoder().decode(base64Key))
                }
                if (signingKeyId != null) {
                    useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
                    sign(publications)
                }
            }
        }
    }
}

val sampleProjects: Set<Project> = project(":sample").allprojects

apiValidation {
    ignoredPackages.add("dev.icerock.moko.mvvm.internal")

    ignoredProjects.addAll(sampleProjects.map { it.name })
}

tasks.register("clean", Delete::class).configure {
    group = "build"
    delete(rootProject.buildDir)
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaMultiModuleTask>().all {
    removeChildTasks(sampleProjects.plus(project(":mvvm")))

    doLast {
        val dir = outputDirectory.get()
        val from = File(dir, "-modules.html")
        val to = File(dir, "index.html")

        from.renameTo(to)

        dir.renameTo(file("docs"))
    }
}
