/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

fun org.gradle.kotlin.dsl.DependencyHandlerScope.mppTestLibrary(
    library: MultiPlatformLibrary
) {
    library.android?.let { "androidTestImplementation"(it) }
    library.common?.let { "commonTestImplementation"(it) }
    library.iosX64?.let { "iosX64TestImplementation"(it) }
    library.iosArm64?.let { "iosArm64TestImplementation"(it) }
}
