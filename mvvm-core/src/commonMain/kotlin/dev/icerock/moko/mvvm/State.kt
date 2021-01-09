/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm

sealed class State<out T, out E> {
    data class Data<out T, out E>(val data: T) : State<T, E>()
    data class Error<out T, out E>(val error: E) : State<T, E>()
    class Loading<out T, out E> : State<T, E>()
    class Empty<out T, out E> : State<T, E>()

    fun isLoading(): Boolean = this is Loading
    fun isSuccess(): Boolean = this is Data
    fun isEmpty(): Boolean = this is Empty
    fun isError(): Boolean = this is Error

    fun dataValue(): T? = (this as? Data)?.data

    fun errorValue(): E? = (this as? Error)?.error
}
