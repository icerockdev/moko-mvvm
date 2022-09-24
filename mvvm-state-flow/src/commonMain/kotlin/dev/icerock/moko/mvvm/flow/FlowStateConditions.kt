/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("MaximumLineLength", "MaxLineLength")

package dev.icerock.moko.mvvm.flow

import dev.icerock.moko.mvvm.state.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

fun <TData, TError> Flow<ResourceState<TData, TError>>.isSuccessState(): Flow<Boolean> =
    map { it.isSuccess() }

fun <TData, TError> Flow<ResourceState<TData, TError>>.isLoadingState(): Flow<Boolean> =
    map { it.isLoading() }

fun <TData, TError> Flow<ResourceState<TData, TError>>.isErrorState(): Flow<Boolean> =
    map { it.isFailed() }

fun <TData, TError> Flow<ResourceState<TData, TError>>.isEmptyState(): Flow<Boolean> =
    map { it.isEmpty() }

inline fun <TData, TError, reified ST : ResourceState<TData, TError>, FL : Flow<ST>> List<FL>.isSuccessState(): Flow<Boolean> =
    combine(this) { list ->
        list.firstOrNull { it !is ResourceState.Success<*, *> } == null
    }

inline fun <TData, TError, reified ST : ResourceState<TData, TError>, F : Flow<ST>> List<F>.isLoadingState(): Flow<Boolean> =
    combine(this) { list ->
        list.firstOrNull() { it is ResourceState.Loading<*, *> } != null
    }

inline fun <TData, TError, reified ST : ResourceState<TData, TError>, F : Flow<ST>> List<F>.isErrorState(): Flow<Boolean> =
    combine(this) { list ->
        list.firstOrNull { it is ResourceState.Failed<*, *> } != null
    }

inline fun <TData, TError, reified ST : ResourceState<TData, TError>, F : Flow<ST>> List<F>.isEmptyState(): Flow<Boolean> =
    combine(this) { list ->
        list.firstOrNull { it is ResourceState.Empty<*, *> } != null
    }
