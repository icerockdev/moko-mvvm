import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    id("detekt-convention")
    id("org.jetbrains.kotlin.multiplatform")
    id("dev.icerock.mobile.multiplatform.apple-framework")
    id("publication-convention")
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

val swiftXCFrameworkProject = File(projectDir, "xcode/mokoMvvmFlow.xcodeproj")
val swiftXCFrameworkOutput = File(projectDir, "xcode/xcframework")
val swiftXCFramework = File(swiftXCFrameworkOutput, "mokoMvvmFlowSwiftUI.xcframework")
val swiftXCFrameworkArchive = File(swiftXCFrameworkOutput, "mokoMvvmFlowSwiftUI.xcframework.zip")

val compileTask = tasks.create("compileMokoFlowSwiftUIXCFramework", Exec::class) {
    group = "xcode"

    commandLine = listOf(
        "xcodebuild",
        "-scheme",
        "mokoMvvmFlowSwiftUI",
        "-project",
        swiftXCFrameworkProject.absolutePath,
        "build"
    )

    dependsOn("assembleMultiPlatformLibraryDebugXCFramework")
}

val archiveTask = tasks.create("archiveMokoFlowSwiftUIXCFramework", Zip::class) {
    group = "xcode"

    from(swiftXCFramework)
    into(swiftXCFramework.name)

    archiveFileName.set(swiftXCFrameworkArchive.name)
    destinationDirectory.set(swiftXCFrameworkOutput)

    dependsOn(compileTask)
}

publishing {
    publications {
        create<MavenPublication>("swiftuiAdditions") {
            artifactId = "mvvm-flow-swiftui"

            artifact(archiveTask.archiveFile) {
                extension = "zip"
            }

            pom {
                packaging = "zip"
            }
        }
    }
}
