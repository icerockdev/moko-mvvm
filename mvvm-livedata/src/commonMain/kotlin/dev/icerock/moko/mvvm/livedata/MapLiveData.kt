/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

private class MapLiveData<T, OT>(
    private val function: (T) -> OT,
    private val source: LiveData<T>
) : MutableLiveData<OT>(initialValue = function(source.value)) {
    private val observer: (T) -> Unit = { this.value = function(it) }

    override var value: OT
        get() {
            return if (isActive) super.value
            else function(source.value)
        }
        set(value) {
            super.value = value
        }

    override fun onActive() {
        source.addObserver(observer)
    }

    override fun onInactive() {
        source.removeObserver(observer)
    }
}

fun <T, OT> LiveData<T>.map(function: (T) -> OT): LiveData<OT> {
    return MapLiveData(function = function, source = this)
}
