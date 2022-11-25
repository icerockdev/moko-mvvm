/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.state.flow

import dev.icerock.moko.mvvm.state.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

fun <T, E> Flow<ResourceState<T, E>>.isSuccessState(): Flow<Boolean> = map { it.isSuccess() }

fun <T, E> Flow<ResourceState<T, E>>.isLoadingState(): Flow<Boolean> = map { it.isLoading() }

fun <T, E> Flow<ResourceState<T, E>>.isErrorState(): Flow<Boolean> = map { it.isFailed() }

fun <T, E> Flow<ResourceState<T, E>>.isEmptyState(): Flow<Boolean> = map { it.isEmpty() }

inline fun <T, E, reified ST : ResourceState<T, E>, FL : Flow<ST>> List<FL>.isSuccessState(): Flow<Boolean> =
    combine(this) { list ->
        list.firstOrNull { it !is ResourceState.Success<*, *> } == null
    }

inline fun <T, E, reified ST : ResourceState<T, E>, FL : Flow<ST>> List<FL>.isLoadingState(): Flow<Boolean> =
    combine(this) { list ->
        list.firstOrNull() { it is ResourceState.Loading<*, *> } != null
    }

inline fun <T, E, reified ST : ResourceState<T, E>, FL : Flow<ST>> List<FL>.isErrorState(): Flow<Boolean> =
    combine(this) { list ->
        list.firstOrNull { it is ResourceState.Failed<*, *> } != null
    }

inline fun <T, E, reified ST : ResourceState<T, E>, FL : Flow<ST>> List<FL>.isEmptyState(): Flow<Boolean> =
    combine(this) { list ->
        list.firstOrNull { it is ResourceState.Empty<*, *> } != null
    }
