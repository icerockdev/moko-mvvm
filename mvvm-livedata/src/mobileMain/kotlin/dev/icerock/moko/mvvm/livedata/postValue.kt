/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

expect fun <T> MutableLiveData<T>.postValue(value: T)

fun <T> MutableLiveData<T>.setValue(value: T, async: Boolean) {
    if (async) {
        this.postValue(value)
    } else {
        this.value = value
    }
}
