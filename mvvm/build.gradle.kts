/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    plugin(Deps.Plugins.androidLibrary)
    plugin(Deps.Plugins.kotlinMultiplatform)
    plugin(Deps.Plugins.kotlinKapt)
    plugin(Deps.Plugins.mobileMultiplatform)
    plugin(Deps.Plugins.mavenPublish)
}

group = "dev.icerock.moko"
version = Deps.mokoMvvmVersion

android {
    buildFeatures.dataBinding = true
}

dependencies {
    commonMainImplementation(Deps.Libs.MultiPlatform.coroutines)

    commonMainImplementation(Deps.Libs.MultiPlatform.mokoResources.common)

    androidMainImplementation(Deps.Libs.Android.appCompat)
    androidMainImplementation(Deps.Libs.Android.lifecycle)
    androidMainImplementation(Deps.Libs.Android.material)
    androidMainImplementation(Deps.Libs.Android.coroutines)

    // fix of package javax.annotation does not exist import javax.annotation.Generated in DataBinding code
    androidMainCompileOnly("javax.annotation:jsr250-api:1.0")

    commonTestImplementation(Deps.Libs.Tests.kotlinTestJUnit)
    androidTestImplementation(Deps.Libs.Tests.androidCoreTesting)
}

publishing {
    repositories.maven("https://api.bintray.com/maven/icerockdev/moko/moko-mvvm/;publish=1") {
        name = "bintray"

        credentials {
            username = System.getProperty("BINTRAY_USER")
            password = System.getProperty("BINTRAY_KEY")
        }
    }
}
