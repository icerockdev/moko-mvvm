/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.State

fun <T1, E, T2, OT> LiveData<State<T1, E>>.concatData(
    liveData: LiveData<State<T2, E>>,
    function: (T1, T2) -> OT
): LiveData<State<OT, E>> =
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
