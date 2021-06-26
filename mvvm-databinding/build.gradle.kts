/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("org.gradle.maven-publish")
}

android {
    buildFeatures.dataBinding = true

    sourceSets.all { java.srcDir("src/$name/kotlin") }
}

dependencies {
    implementation(libs.coroutines)

    api(projects.mvvmLivedata)

    api(libs.mokoResources)

    api(libs.appCompat)
    api(libs.lifecycle)
    api(libs.material)
    api(libs.coroutines)

    // fix of package javax.annotation does not exist import javax.annotation.Generated in DataBinding code
    compileOnly("javax.annotation:jsr250-api:1.0")
}

afterEvaluate {
    publishing.publications {
        create("release", MavenPublication::class.java) {
            from(components.getByName("release"))
        }
    }
}
