/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.state

sealed class ResourceState<out TData, out TError> {
    data class Success<out TData>(val data: TData) : ResourceState<TData, Nothing>()
    data class Failed<out TError>(val error: TError) : ResourceState<Nothing, TError>()
    object Loading : ResourceState<Nothing, Nothing>()
    object Empty : ResourceState<Nothing, Nothing>()

    fun isLoading(): Boolean = this is Loading
    fun isSuccess(): Boolean = this is Success
    fun isEmpty(): Boolean = this is Empty
    fun isFailed(): Boolean = this is Failed

    fun dataValue(): TData? = (this as? Success)?.data

    fun errorValue(): TError? = (this as? Failed)?.error
}
