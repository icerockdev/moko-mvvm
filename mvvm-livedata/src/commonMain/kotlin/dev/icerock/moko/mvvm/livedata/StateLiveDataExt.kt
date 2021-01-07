/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.State

fun <T, E> LiveData<State<T, E>>.isSuccessState(): LiveData<Boolean> = map { it.isSuccess() }

fun <T, E> LiveData<State<T, E>>.isLoadingState(): LiveData<Boolean> = map { it.isLoading() }

fun <T, E> LiveData<State<T, E>>.isErrorState(): LiveData<Boolean> = map { it.isError() }

fun <T, E> LiveData<State<T, E>>.isEmptyState(): LiveData<Boolean> = map { it.isEmpty() }

fun <T, E> LiveData<State<T, E>>.data(): LiveData<T?> = map { it.dataValue() }

fun <T, E> LiveData<State<T, E>>.dataValue(): T? = value.dataValue()

fun <T, E> LiveData<State<T, E>>.error(): LiveData<E?> = map { it.errorValue() }

fun <T, E> LiveData<State<T, E>>.errorValue(): E? = value.errorValue()

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

fun <T, E, ST : State<T, E>, LD : LiveData<out ST>> List<LD>.error(): LiveData<E?> =
    MediatorLiveData<E?>(null)
        .composition(this) { list ->
            @Suppress("UNCHECKED_CAST")
            val errorItem = list.firstOrNull {
                (it is State.Error<*, *>)
            } as? State.Error<T, E>
            errorItem?.error
        }

fun <IT, E, OT> LiveData<State<IT, E>>.dataTransform(transform: LiveData<IT>.() -> LiveData<OT>):
        LiveData<State<OT, E>> = flatMap { state ->
    when (state) {
        is State.Data -> transform.invoke(MutableLiveData(state.data)).map { State.Data<OT, E>(it) as State<OT, E> }
        is State.Loading -> MutableLiveData<State<OT, E>>(State.Loading())
        is State.Empty -> MutableLiveData<State<OT, E>>(State.Empty())
        is State.Error -> MutableLiveData<State<OT, E>>(State.Error(state.error))
    }
}

fun <T, IE, OE> LiveData<State<T, IE>>.errorTransform(transform: LiveData<IE>.() -> LiveData<OE>):
        LiveData<State<T, OE>> = flatMap { state ->
    when (state) {
        is State.Data -> MutableLiveData<State<T, OE>>(State.Data(state.data))
        is State.Loading -> MutableLiveData<State<T, OE>>(State.Loading())
        is State.Empty -> MutableLiveData<State<T, OE>>(State.Empty())
        is State.Error -> transform.invoke(MutableLiveData(state.error)).map { State.Error<T, OE>(it) as State<T, OE> }
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

fun <T1, E, T2, OT> LiveData<State<T1, E>>.concatData(
    liveData: LiveData<State<T2, E>>,
    function: (T1, T2) -> OT
):
        LiveData<State<OT, E>> =
    mergeWith(liveData) { firstState, secondState ->
        val state: State<OT, E> = when {
            (firstState is State.Loading || secondState is State.Loading) -> State.Loading()
            (firstState is State.Error) -> State.Error(firstState.error)
            (secondState is State.Error) -> State.Error(secondState.error)
            (firstState is State.Empty || secondState is State.Empty) -> State.Empty()
            (firstState is State.Data && secondState is State.Data) -> State.Data(
                function(
                    firstState.data,
                    secondState.data
                )
            )
            else -> State.Empty()
        }

        state
    }

fun <T, E> LiveData<State<T, E>>.emptyIf(emptyPredicate: (T) -> Boolean):
        LiveData<State<T, E>> = map {
    when {
        it is State.Data && emptyPredicate(it.data) -> State.Empty()
        else -> it
    }
}
