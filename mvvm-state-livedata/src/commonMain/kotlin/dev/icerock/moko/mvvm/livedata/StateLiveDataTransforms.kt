/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.state.ResourceState
import dev.icerock.moko.mvvm.state.livedata.dataTransform
import dev.icerock.moko.mvvm.state.livedata.emptyAsData
import dev.icerock.moko.mvvm.state.livedata.emptyAsError
import dev.icerock.moko.mvvm.state.livedata.emptyIf
import dev.icerock.moko.mvvm.state.livedata.errorTransform

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("dataTransform", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <IT, E, OT> LiveData<ResourceState<IT, E>>.dataTransform(
    transform: LiveData<IT>.() -> LiveData<OT>
): LiveData<ResourceState<OT, E>> = dataTransform(transform)

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("errorTransform", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, IE, OE> LiveData<ResourceState<T, IE>>.errorTransform(
    transform: LiveData<IE>.() -> LiveData<OE>
): LiveData<ResourceState<T, OE>> = errorTransform(transform)

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("emptyAsError", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E> LiveData<ResourceState<T, E>>.emptyAsError(
    errorBuilder: () -> E
): LiveData<ResourceState<T, E>> = emptyAsError(errorBuilder)

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("emptyAsData", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E> LiveData<ResourceState<T, E>>.emptyAsData(
    dataBuilder: () -> T
): LiveData<ResourceState<T, E>> = emptyAsData(dataBuilder)

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("emptyIf", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E> LiveData<ResourceState<T, E>>.emptyIf(
    emptyPredicate: (T) -> Boolean
): LiveData<ResourceState<T, E>> = emptyIf(emptyPredicate)
