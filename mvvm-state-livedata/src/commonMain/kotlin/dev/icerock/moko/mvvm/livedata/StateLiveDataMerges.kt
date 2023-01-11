/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.state.ResourceState
import dev.icerock.moko.mvvm.state.livedata.concatData


@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("concatData", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T1, E, T2, OT> LiveData<ResourceState<T1, E>>.concatData(
    liveData: LiveData<ResourceState<T2, E>>,
    function: (T1, T2) -> OT
): LiveData<ResourceState<OT, E>> = concatData(liveData, function)
