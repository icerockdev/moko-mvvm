/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm

@Deprecated(
    replaceWith = ReplaceWith(
        expression = "ResourceState",
        imports = arrayOf("dev.icerock.moko.mvvm.ResourceState")
    ),
    message = "State will be removed in 0.11.0, please use ResourceState from mvvm-state instead. " +
            "More info at https://github.com/icerockdev/moko-mvvm/issues/47",
    level = DeprecationLevel.WARNING
)
sealed class State<out T, out E> {
    @Deprecated(
        replaceWith = ReplaceWith(
            expression = "ResourceState.Success",
            imports = arrayOf("dev.icerock.moko.mvvm.ResourceState")
        ),
        message = "State.Data will be removed in 0.11.0, please use ResourceState.Success from mvvm-state instead. " +
                "More info at https://github.com/icerockdev/moko-mvvm/issues/47",
        level = DeprecationLevel.WARNING
    )
    data class Data<out T, out E>(val data: T) : State<T, E>()

    @Deprecated(
        replaceWith = ReplaceWith(
            expression = "ResourceState.Failed",
            imports = arrayOf("dev.icerock.moko.mvvm.ResourceState")
        ),
        message = "State.Error will be removed in 0.11.0, please use ResourceState.Failed from mvvm-state instead. " +
                "More info at https://github.com/icerockdev/moko-mvvm/issues/47",
        level = DeprecationLevel.WARNING
    )
    data class Error<out T, out E>(val error: E) : State<T, E>()
    class Loading<out T, out E> : State<T, E>()
    class Empty<out T, out E> : State<T, E>()

    fun isLoading(): Boolean = this is Loading
    fun isSuccess(): Boolean = this is Data
    fun isEmpty(): Boolean = this is Empty

    @Deprecated(
        replaceWith = ReplaceWith(expression = "isFailed"),
        message = "isError should be replaced to isFailed of ResourceState from mvvm-state. " +
                "More info at https://github.com/icerockdev/moko-mvvm/issues/47",
        level = DeprecationLevel.WARNING
    )
    fun isError(): Boolean = this is Error

    fun dataValue(): T? = (this as? Data)?.data

    fun errorValue(): E? = (this as? Error)?.error
}
