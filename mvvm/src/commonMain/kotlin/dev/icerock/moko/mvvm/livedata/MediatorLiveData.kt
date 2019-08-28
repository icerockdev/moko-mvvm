/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

expect open class MediatorLiveData<T>(initialValue: T) : MutableLiveData<T> {
    fun <IT> addSource(
        liveData: LiveData<IT>,
        onChange: (newValue: IT) -> Unit
    )
}
