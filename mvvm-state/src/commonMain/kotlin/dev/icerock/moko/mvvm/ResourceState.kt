/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm

sealed class ResourceState<out TData, out TError> {
    data class Success<out TData, out TError>(val data: TData) : ResourceState<TData, TError>()
    data class Failed<out TData, out TError>(val error: TError) : ResourceState<TData, TError>()
    class Loading<out TData, out TError> : ResourceState<TData, TError>()
    class Empty<out TData, out TError> : ResourceState<TData, TError>()

    fun isLoading(): Boolean = this is Loading
    fun isSuccess(): Boolean = this is Success
    fun isEmpty(): Boolean = this is Empty
    fun isFailed(): Boolean = this is Failed

    fun dataValue(): TData? = (this as? Success)?.data

    fun errorValue(): TError? = (this as? Failed)?.error
}
