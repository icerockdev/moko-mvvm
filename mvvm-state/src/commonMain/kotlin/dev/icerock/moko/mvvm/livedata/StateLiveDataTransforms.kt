/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.ResourceState

fun <IT, E, OT> LiveData<ResourceState<IT, E>>.dataTransform(
    transform: LiveData<IT>.() -> LiveData<OT>
): LiveData<ResourceState<OT, E>> = flatMap { state ->
    when (state) {
        is ResourceState.Success -> transform.invoke(MutableLiveData(state.data))
            .map { ResourceState.Success(it) }
        is ResourceState.Loading -> MutableLiveData(ResourceState.Loading())
        is ResourceState.Empty -> MutableLiveData(ResourceState.Empty())
        is ResourceState.Failed -> MutableLiveData(ResourceState.Failed(state.error))
    }
}

fun <T, IE, OE> LiveData<ResourceState<T, IE>>.errorTransform(
    transform: LiveData<IE>.() -> LiveData<OE>
): LiveData<ResourceState<T, OE>> = flatMap { state ->
    when (state) {
        is ResourceState.Success -> MutableLiveData(ResourceState.Success(state.data))
        is ResourceState.Loading -> MutableLiveData(ResourceState.Loading())
        is ResourceState.Empty -> MutableLiveData(ResourceState.Empty())
        is ResourceState.Failed -> transform.invoke(MutableLiveData(state.error))
            .map { ResourceState.Failed<T, OE>(it) }
    }
}

fun <T, E> LiveData<ResourceState<T, E>>.emptyAsError(
    errorBuilder: () -> E
): LiveData<ResourceState<T, E>> = map {
    when (it) {
        is ResourceState.Empty -> ResourceState.Failed(errorBuilder())
        else -> it
    }
}

fun <T, E> LiveData<ResourceState<T, E>>.emptyAsData(
    dataBuilder: () -> T
): LiveData<ResourceState<T, E>> = map {
    when (it) {
        is ResourceState.Empty -> ResourceState.Success(dataBuilder())
        else -> it
    }
}

fun <T, E> LiveData<ResourceState<T, E>>.emptyIf(
    emptyPredicate: (T) -> Boolean
): LiveData<ResourceState<T, E>> = map {
    when {
        it is ResourceState.Success && emptyPredicate(it.data) -> ResourceState.Empty()
        else -> it
    }
}
