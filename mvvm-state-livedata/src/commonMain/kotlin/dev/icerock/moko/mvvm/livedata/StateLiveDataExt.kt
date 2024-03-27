/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.state.ResourceState
import dev.icerock.moko.mvvm.state.livedata.data
import dev.icerock.moko.mvvm.state.livedata.dataValue
import dev.icerock.moko.mvvm.state.livedata.error
import dev.icerock.moko.mvvm.state.livedata.errorValue

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("data", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E> LiveData<ResourceState<T, E>>.data(): LiveData<T?> = this.data()

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("dataValue", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E> LiveData<ResourceState<T, E>>.dataValue(): T? = this.dataValue()

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("error", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E> LiveData<ResourceState<T, E>>.error(): LiveData<E?> = this.error()

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("errorValue", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E> LiveData<ResourceState<T, E>>.errorValue(): E? = this.errorValue()

@Deprecated(
    message = "deprecated due to package renaming",
    replaceWith = ReplaceWith("error", "dev.icerock.moko.mvvm.state.livedata"),
    level = DeprecationLevel.WARNING
)
fun <T, E, ST : ResourceState<T, E>, LD : LiveData<out ST>> List<LD>.error(): LiveData<E?> =
    this.error()
