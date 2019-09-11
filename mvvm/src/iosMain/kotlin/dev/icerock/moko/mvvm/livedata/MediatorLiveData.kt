/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

actual open class MediatorLiveData<T> actual constructor(initialValue: T) :
    MutableLiveData<T>(initialValue) {

    actual fun <IT> addSource(
        liveData: LiveData<IT>,
        onChange: (newValue: IT) -> Unit
    ) {
        liveData.addObserver { onChange(it) }
    }
}
