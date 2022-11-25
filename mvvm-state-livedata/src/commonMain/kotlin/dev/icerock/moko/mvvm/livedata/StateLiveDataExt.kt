/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.ResourceState

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("data", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E> LiveData<ResourceState<T, E>>.data(): LiveData<T?> = map { it.dataValue() }

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("dataValue", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E> LiveData<ResourceState<T, E>>.dataValue(): T? = value.dataValue()

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("error", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E> LiveData<ResourceState<T, E>>.error(): LiveData<E?> = map { it.errorValue() }

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("errorValue", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E> LiveData<ResourceState<T, E>>.errorValue(): E? = value.errorValue()

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("error", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E, ST : ResourceState<T, E>, LD : LiveData<out ST>> List<LD>.error(): LiveData<E?> =
    MediatorLiveData<E?>(null)
        .composition(this) { list ->
            @Suppress("UNCHECKED_CAST")
            val errorItem = list.firstOrNull {
                (it is ResourceState.Failed<*, *>)
            } as? ResourceState.Failed<T, E>
            errorItem?.error
        }
