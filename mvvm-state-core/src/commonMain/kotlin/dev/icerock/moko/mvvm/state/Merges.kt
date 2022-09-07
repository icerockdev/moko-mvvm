/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.state

fun <T1, E, T2, OT> mergeState(
    firstState: ResourceState<T1, E>,
    secondState: ResourceState<T2, E>,
    function: (T1, T2) -> OT
): ResourceState<OT, E> = when {
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
