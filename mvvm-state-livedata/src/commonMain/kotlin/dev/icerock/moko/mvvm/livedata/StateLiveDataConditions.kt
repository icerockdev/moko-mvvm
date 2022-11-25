/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.ResourceState

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("isSuccessState", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E> LiveData<ResourceState<T, E>>.isSuccessState(): LiveData<Boolean> = map { it.isSuccess() }

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("isLoadingState", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E> LiveData<ResourceState<T, E>>.isLoadingState(): LiveData<Boolean> = map { it.isLoading() }

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("isErrorState", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E> LiveData<ResourceState<T, E>>.isErrorState(): LiveData<Boolean> = map { it.isFailed() }

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("isEmptyState", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E> LiveData<ResourceState<T, E>>.isEmptyState(): LiveData<Boolean> = map { it.isEmpty() }

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("isSuccessState", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E, ST : ResourceState<T, E>, LD : LiveData<out ST>> List<LD>.isSuccessState(): LiveData<Boolean> =
    MediatorLiveData(false)
        .composition(this) { list ->
            list.firstOrNull { it !is ResourceState.Success<*, *> } == null
        }

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("isLoadingState", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E, ST : ResourceState<T, E>, LD : LiveData<out ST>> List<LD>.isLoadingState(): LiveData<Boolean> =
    MediatorLiveData(false)
        .composition(this) { list ->
            list.firstOrNull { it is ResourceState.Loading<*, *> } != null
        }

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("isErrorState", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E, ST : ResourceState<T, E>, LD : LiveData<out ST>> List<LD>.isErrorState(): LiveData<Boolean> =
    MediatorLiveData(false)
        .composition(this) { list ->
            list.firstOrNull { it is ResourceState.Failed<*, *> } != null
        }

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("isEmptyState", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E, ST : ResourceState<T, E>, LD : LiveData<out ST>> List<LD>.isEmptyState(): LiveData<Boolean> =
    MediatorLiveData(false)
        .composition(this) { list ->
            list.firstOrNull { it is ResourceState.Empty<*, *> } != null
        }
