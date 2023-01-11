/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.state.ResourceState
import dev.icerock.moko.mvvm.state.livedata.isEmptyState
import dev.icerock.moko.mvvm.state.livedata.isErrorState
import dev.icerock.moko.mvvm.state.livedata.isLoadingState
import dev.icerock.moko.mvvm.state.livedata.isSuccessState


@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("isSuccessState", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E> LiveData<ResourceState<T, E>>.isSuccessState(): LiveData<Boolean> = this.isSuccessState()

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("isLoadingState", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E> LiveData<ResourceState<T, E>>.isLoadingState(): LiveData<Boolean> = this.isLoadingState()

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("isErrorState", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E> LiveData<ResourceState<T, E>>.isErrorState(): LiveData<Boolean> = this.isErrorState()

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("isEmptyState", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E> LiveData<ResourceState<T, E>>.isEmptyState(): LiveData<Boolean> = this.isEmptyState()

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("isSuccessState", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E, ST : ResourceState<T, E>, LD : LiveData<out ST>> List<LD>.isSuccessState(): LiveData<Boolean> =
    this.isSuccessState()

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("isLoadingState", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E, ST : ResourceState<T, E>, LD : LiveData<out ST>> List<LD>.isLoadingState(): LiveData<Boolean> =
    this.isLoadingState()

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("isErrorState", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E, ST : ResourceState<T, E>, LD : LiveData<out ST>> List<LD>.isErrorState(): LiveData<Boolean> =
    this.isErrorState()

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("isEmptyState", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E, ST : ResourceState<T, E>, LD : LiveData<out ST>> List<LD>.isEmptyState(): LiveData<Boolean> =
    this.isEmptyState()
