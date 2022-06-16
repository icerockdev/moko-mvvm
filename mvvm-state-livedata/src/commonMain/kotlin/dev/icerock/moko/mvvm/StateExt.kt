/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm

import dev.icerock.moko.mvvm.state.ResourceState
import dev.icerock.moko.mvvm.state.asState
import dev.icerock.moko.mvvm.state.nullAsEmpty
import dev.icerock.moko.mvvm.state.nullAsLoading

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("asState", "dev.icerock.moko.mvvm.state"),
    level = DeprecationLevel.WARNING
)
fun <T, E> T.asState(): ResourceState<T, E> =
    this.asState()

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("asState", "dev.icerock.moko.mvvm.state"),
    level = DeprecationLevel.WARNING
)
fun <T, E> T?.asState(whenNull: () -> ResourceState<T, E>): ResourceState<T, E> =
    this?.asState() ?: whenNull()

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("asState", "dev.icerock.moko.mvvm.state"),
    level = DeprecationLevel.WARNING
)
fun <T, E> List<T>.asState(): ResourceState<List<T>, E> =
    this.asState()

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("asState", "dev.icerock.moko.mvvm.state"),
    level = DeprecationLevel.WARNING
)
fun <T, E> List<T>?.asState(whenNull: () -> ResourceState<List<T>, E>): ResourceState<List<T>, E> =
    this.asState(whenNull)

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("nullAsEmpty", "dev.icerock.moko.mvvm.state"),
    level = DeprecationLevel.WARNING
)
inline fun <reified T, reified E> ResourceState<T, E>?.nullAsEmpty(): ResourceState<T, E> =
    this.nullAsEmpty()

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("nullAsLoading", "dev.icerock.moko.mvvm.state"),
    level = DeprecationLevel.WARNING
)
inline fun <reified T, reified E> ResourceState<T, E>?.nullAsLoading(): ResourceState<T, E> =
    this.nullAsLoading()
