/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import kotlin.jvm.JvmName

fun <IT, OT> LiveData<IT?>.mapOrNull(function: (IT) -> OT): LiveData<OT?> {
    return map { newValue ->
        when (newValue) {
            null -> null
            else -> function(newValue)
        }
    }
}

fun <OT> LiveData<Boolean?>.mapTrueOrNull(function: () -> OT): LiveData<OT?> {
    return map { newValue ->
        when (newValue) {
            true -> function()
            else -> null
        }
    }
}

fun LiveData<Boolean>.not() = map { !it }

fun <T> LiveData<T?>.required(initialValue: T): LiveData<T> = MediatorLiveData(initialValue).apply {
    addSource(this@required) { newValue ->
        if (newValue == null) return@addSource

        value = newValue
    }
}

fun <T> LiveData<T>.distinct(): LiveData<T> {
    val source = this
    return MediatorLiveData(value).apply {
        addSource(source) { newValue ->
            if (newValue == value) return@addSource
            value = newValue
        }
    }
}

fun LiveData<String?>.orEmpty(): LiveData<String> {
    return map { it.orEmpty() }
}

@JvmName("ListLiveDataOrEmpty")
fun <T> LiveData<List<T>?>.orEmpty(): LiveData<List<T>> {
    return map { it.orEmpty() }
}

fun LiveData<Boolean>.revert() = map { !it }
