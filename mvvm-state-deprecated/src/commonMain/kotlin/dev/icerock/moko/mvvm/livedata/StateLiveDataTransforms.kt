/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.State

fun <IT, E, OT> LiveData<State<IT, E>>.dataTransform(transform: LiveData<IT>.() -> LiveData<OT>):
        LiveData<State<OT, E>> = flatMap { state ->
    when (state) {
        is State.Data -> transform.invoke(MutableLiveData(state.data))
            .map { State.Data<OT, E>(it) as State<OT, E> }
        is State.Loading -> MutableLiveData(State.Loading())
        is State.Empty -> MutableLiveData(State.Empty())
        is State.Error -> MutableLiveData(State.Error(state.error))
    }
}

fun <T, IE, OE> LiveData<State<T, IE>>.errorTransform(transform: LiveData<IE>.() -> LiveData<OE>):
        LiveData<State<T, OE>> = flatMap { state ->
    when (state) {
        is State.Data -> MutableLiveData(State.Data(state.data))
        is State.Loading -> MutableLiveData(State.Loading())
        is State.Empty -> MutableLiveData(State.Empty())
        is State.Error -> transform.invoke(MutableLiveData(state.error))
            .map { State.Error<T, OE>(it) as State<T, OE> }
    }
}

fun <T, E> LiveData<State<T, E>>.emptyAsError(errorBuilder: () -> E):
        LiveData<State<T, E>> = map {
    when (it) {
        is State.Empty -> State.Error(errorBuilder())
        else -> it
    }
}

fun <T, E> LiveData<State<T, E>>.emptyAsData(dataBuilder: () -> T):
        LiveData<State<T, E>> = map {
    when (it) {
        is State.Empty -> State.Data(dataBuilder())
        else -> it
    }
}

fun <T, E> LiveData<State<T, E>>.emptyIf(emptyPredicate: (T) -> Boolean):
        LiveData<State<T, E>> = map {
    when {
        it is State.Data && emptyPredicate(it.data) -> State.Empty()
        else -> it
    }
}
