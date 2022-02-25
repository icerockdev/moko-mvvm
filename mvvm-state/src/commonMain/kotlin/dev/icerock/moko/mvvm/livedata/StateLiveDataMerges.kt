/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.ResourceState

fun <T1, E, T2, OT> LiveData<ResourceState<T1, E>>.concatData(
    liveData: LiveData<ResourceState<T2, E>>,
    function: (T1, T2) -> OT
): LiveData<ResourceState<OT, E>> =
    mediatorOf(this, liveData) { firstState, secondState ->
        val state: ResourceState<OT, E> = when {
            (firstState is ResourceState.Loading || secondState is ResourceState.Loading) -> ResourceState.Loading()
            (firstState is ResourceState.Failed) -> ResourceState.Failed(firstState.error)
            (secondState is ResourceState.Failed) -> ResourceState.Failed(secondState.error)
            (firstState is ResourceState.Empty || secondState is ResourceState.Empty) -> ResourceState.Empty()
            (firstState is ResourceState.Success && secondState is ResourceState.Success) -> ResourceState.Success(
                function(
                    firstState.data,
                    secondState.data
                )
            )
            else -> ResourceState.Empty()
        }

        state
    }
