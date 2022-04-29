import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    id("detekt-convention")
    id("org.jetbrains.kotlin.multiplatform")
    id("dev.icerock.mobile.multiplatform.apple-framework")
}

kotlin {
    iosArm64()
    iosX64()
    iosSimulatorArm64()
    macosX64()

    val xcf = XCFramework("MultiPlatformLibrary")
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>()
        .configureEach {
            binaries.withType<org.jetbrains.kotlin.gradle.plugin.mpp.Framework>().configureEach {
                xcf.add(this)
            }
        }
}

dependencies {
    commonMainImplementation(libs.coroutines)

    commonMainApi(libs.mokoResources)
    commonMainApi(projects.mvvmFlow)
    commonMainApi(projects.mvvmCore)
}

framework {
    export(projects.mvvmCore)
    export(projects.mvvmFlow)
    export(libs.mokoResources)
}
