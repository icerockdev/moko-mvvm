/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.State

fun <T, E> LiveData<State<T, E>>.data(): LiveData<T?> = map { it.dataValue() }

fun <T, E> LiveData<State<T, E>>.dataValue(): T? = value.dataValue()

fun <T, E> LiveData<State<T, E>>.error(): LiveData<E?> = map { it.errorValue() }

fun <T, E> LiveData<State<T, E>>.errorValue(): E? = value.errorValue()

fun <T, E, ST : State<T, E>, LD : LiveData<out ST>> List<LD>.error(): LiveData<E?> =
    MediatorLiveData<E?>(null)
        .composition(this) { list ->
            @Suppress("UNCHECKED_CAST")
            val errorItem = list.firstOrNull {
                (it is State.Error<*, *>)
            } as? State.Error<T, E>
            errorItem?.error
        }
