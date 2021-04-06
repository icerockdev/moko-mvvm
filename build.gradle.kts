/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import java.util.Base64

plugins {
    plugin(Deps.Plugins.detekt) apply false
    plugin(Deps.Plugins.dokka) apply false
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.5.0"
}

buildscript {
    repositories {
        gradlePluginPortal()
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
        mavenCentral()

        jcenter {
            content {
                includeGroup("org.jetbrains.trove4j")
                includeModule("org.jetbrains.kotlinx", "kotlinx-html-jvm")
            }
        }
    }

    apply(plugin = Deps.Plugins.detekt.id)
    apply(plugin = Deps.Plugins.dokka.id)

    configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
        input.setFrom(
            "src/commonMain/kotlin",
            "src/androidMain/kotlin",
            "src/iosMain/kotlin",
            "src/main/kotlin"
        )
    }

    dependencies {
        "detektPlugins"(Deps.Libs.Jvm.detektFormatting)
    }

    plugins.withId(Deps.Plugins.androidLibrary.id) {
        configure<com.android.build.gradle.LibraryExtension> {
            compileSdkVersion(Deps.Android.compileSdk)

            defaultConfig {
                minSdkVersion(Deps.Android.minSdk)
                targetSdkVersion(Deps.Android.targetSdk)
            }
        }
    }

    plugins.withId(Deps.Plugins.mavenPublish.id) {
        group = "dev.icerock.moko"
        version = Deps.mokoMvvmVersion

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

            apply(plugin = Deps.Plugins.signing.id)

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
