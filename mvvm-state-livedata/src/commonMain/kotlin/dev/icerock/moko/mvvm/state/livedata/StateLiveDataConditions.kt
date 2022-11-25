/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.state.livedata

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MediatorLiveData
import dev.icerock.moko.mvvm.livedata.composition
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.mvvm.state.ResourceState

fun <T, E> LiveData<ResourceState<T, E>>.isSuccessState(): LiveData<Boolean> = map { it.isSuccess() }

fun <T, E> LiveData<ResourceState<T, E>>.isLoadingState(): LiveData<Boolean> = map { it.isLoading() }

fun <T, E> LiveData<ResourceState<T, E>>.isErrorState(): LiveData<Boolean> = map { it.isFailed() }

fun <T, E> LiveData<ResourceState<T, E>>.isEmptyState(): LiveData<Boolean> = map { it.isEmpty() }

fun <T, E, ST : ResourceState<T, E>, LD : LiveData<out ST>> List<LD>.isSuccessState(): LiveData<Boolean> =
    MediatorLiveData(false)
        .composition(this) { list ->
            list.firstOrNull { it !is ResourceState.Success<*, *> } == null
        }

fun <T, E, ST : ResourceState<T, E>, LD : LiveData<out ST>> List<LD>.isLoadingState(): LiveData<Boolean> =
    MediatorLiveData(false)
        .composition(this) { list ->
            list.firstOrNull { it is ResourceState.Loading<*, *> } != null
        }

fun <T, E, ST : ResourceState<T, E>, LD : LiveData<out ST>> List<LD>.isErrorState(): LiveData<Boolean> =
    MediatorLiveData(false)
        .composition(this) { list ->
            list.firstOrNull { it is ResourceState.Failed<*, *> } != null
        }

fun <T, E, ST : ResourceState<T, E>, LD : LiveData<out ST>> List<LD>.isEmptyState(): LiveData<Boolean> =
    MediatorLiveData(false)
        .composition(this) { list ->
            list.firstOrNull { it is ResourceState.Empty<*, *> } != null
        }
