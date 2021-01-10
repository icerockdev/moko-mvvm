/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

fun <T> MutableLiveData<T>.setValue(value: T, async: Boolean) {
    if (async) {
        this.postValue(value)
    } else {
        this.value = value
    }
}

fun <T> MutableLiveData<T>.readOnly(): LiveData<T> = this
