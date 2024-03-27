/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */
@file:OptIn(ExperimentalCoroutinesApi::class)

package dev.icerock.moko.mvvm.state.flow

import dev.icerock.moko.mvvm.state.ResourceState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

fun <IT, E, OT> Flow<ResourceState<IT, E>>.dataTransform(
    transform: Flow<IT>.() -> Flow<OT>
): Flow<ResourceState<OT, E>> = flatMapLatest { state ->
    when (state) {
        is ResourceState.Success -> transform.invoke(flowOf(state.data))
            .map { ResourceState.Success(it) }
        is ResourceState.Empty -> flowOf(ResourceState.Empty())
        is ResourceState.Failed -> flowOf(ResourceState.Failed(state.error))
        is ResourceState.Loading -> flowOf(ResourceState.Loading())
    }
}

fun <T, IE, OE> Flow<ResourceState<T, IE>>.errorTransform(
    transform: Flow<IE>.() -> Flow<OE>
): Flow<ResourceState<T, OE>> = flatMapLatest { state ->
    when (state) {
        is ResourceState.Success -> flowOf(ResourceState.Success(state.data))
        is ResourceState.Loading -> flowOf(ResourceState.Loading())
        is ResourceState.Empty -> flowOf(ResourceState.Empty())
        is ResourceState.Failed -> transform.invoke(flowOf(state.error))
            .map { ResourceState.Failed(it) }
    }
}

fun <T, E> Flow<ResourceState<T, E>>.emptyAsError(
    errorBuilder: () -> E
): Flow<ResourceState<T, E>> = map {
    when (it) {
        is ResourceState.Empty -> ResourceState.Failed(errorBuilder())
        else -> it
    }
}

fun <T, E> Flow<ResourceState<T, E>>.emptyAsData(
    dataBuilder: () -> T
): Flow<ResourceState<T, E>> = map {
    when (it) {
        is ResourceState.Empty -> ResourceState.Success(dataBuilder())
        else -> it
    }
}

fun <T, E> Flow<ResourceState<T, E>>.emptyIf(
    emptyPredicate: (T) -> Boolean
): Flow<ResourceState<T, E>> = map {
    when {
        it is ResourceState.Success && emptyPredicate(it.data) -> ResourceState.Empty()
        else -> it
    }
}
