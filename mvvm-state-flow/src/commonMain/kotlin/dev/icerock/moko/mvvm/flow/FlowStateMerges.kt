/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.flow

import dev.icerock.moko.mvvm.state.ResourceState
import dev.icerock.moko.mvvm.state.mergeState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

fun <T1, E, T2, OT> Flow<ResourceState<T1, E>>.concatData(
    flow: Flow<ResourceState<T2, E>>,
    function: (T1, T2) -> OT
): Flow<ResourceState<OT, E>> =
    combine(
        this, flow
    ) { firstState, secondState ->
        mergeState(firstState, secondState, function)
    }
