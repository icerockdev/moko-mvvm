/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.State

fun <T, E> LiveData<State<T, E>>.isSuccessState(): LiveData<Boolean> = map { it.isSuccess() }

fun <T, E> LiveData<State<T, E>>.isLoadingState(): LiveData<Boolean> = map { it.isLoading() }

fun <T, E> LiveData<State<T, E>>.isErrorState(): LiveData<Boolean> = map { it.isError() }

fun <T, E> LiveData<State<T, E>>.isEmptyState(): LiveData<Boolean> = map { it.isEmpty() }

fun <T, E, ST : State<T, E>, LD : LiveData<out ST>> List<LD>.isSuccessState(): LiveData<Boolean> =
    MediatorLiveData(false)
        .composition(this) { list ->
            list.firstOrNull { it !is State.Data<*, *> } == null
        }

fun <T, E, ST : State<T, E>, LD : LiveData<out ST>> List<LD>.isLoadingState(): LiveData<Boolean> =
    MediatorLiveData(false)
        .composition(this) { list ->
            list.firstOrNull { it is State.Loading<*, *> } != null
        }

fun <T, E, ST : State<T, E>, LD : LiveData<out ST>> List<LD>.isErrorState(): LiveData<Boolean> =
    MediatorLiveData(false)
        .composition(this) { list ->
            list.firstOrNull { it is State.Error<*, *> } != null
        }

fun <T, E, ST : State<T, E>, LD : LiveData<out ST>> List<LD>.isEmptyState(): LiveData<Boolean> =
    MediatorLiveData(false)
        .composition(this) { list ->
            list.firstOrNull { it is State.Empty<*, *> } != null
        }
