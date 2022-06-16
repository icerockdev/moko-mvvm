/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.flow

import dev.icerock.moko.mvvm.state.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.map

fun <TData, TError> Flow<ResourceState<TData, TError>>.data(): Flow<TData?> =
    map { it.dataValue() }

fun <TData, TError> StateFlow<ResourceState<TData, TError>>.dataValue(): TData? =
    value.dataValue()

suspend fun <TData, TError> Flow<ResourceState<TData, TError>>.dataValue(): TData? =
    lastOrNull()?.dataValue()

fun <TData, TError> Flow<ResourceState<TData, TError>>.error(): Flow<TError?> =
    map { it.errorValue() }

fun <TData, TError> StateFlow<ResourceState<TData, TError>>.errorValue(): TError? =
    value.errorValue()

suspend fun <TData, TError> Flow<ResourceState<TData, TError>>.errorValue(): TError? =
    lastOrNull()?.errorValue()

inline fun <TData, TError, reified ST : ResourceState<TData, TError>, LD : Flow<ST>> List<LD>.error(): Flow<TError?> =
    combine(this) { list ->
        @Suppress("UNCHECKED_CAST")
        val errorItem = list.firstOrNull {
            (it is ResourceState.Failed<*>)
        } as? ResourceState.Failed<TError>
        errorItem?.error
    }
