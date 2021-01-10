/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import androidx.lifecycle.MediatorLiveData

actual open class MediatorLiveData<T> actual constructor(initialValue: T) :
    MutableLiveData<T>(
        MediatorLiveData<T>().apply { value = initialValue }
    ) {

    actual fun <IT> addSource(
        liveData: LiveData<IT>,
        onChange: (newValue: IT) -> Unit
    ) {
        liveData.addObserver { onChange(it) }
    }
}
