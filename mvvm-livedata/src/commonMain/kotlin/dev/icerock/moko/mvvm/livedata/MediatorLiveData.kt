/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

open class MediatorLiveData<T> constructor(
    initialValue: T
) : MutableLiveData<T>(initialValue) {
    private val sources = mutableListOf<SourceObserver<*>>()

    fun <IT> addSource(
        liveData: LiveData<IT>,
        onChange: (newValue: IT) -> Unit
    ) {
        val observer = SourceObserver(liveData, onChange)
        sources.add(observer)

        if (isActive) observer.activate()
    }

    override fun onActive() {
        super.onActive()

        sources.forEach { it.activate() }
    }

    override fun onInactive() {
        super.onInactive()

        sources.forEach { it.deactivate() }
    }

    private class SourceObserver<T>(
        private val liveData: LiveData<T>,
        private val onChange: (newValue: T) -> Unit
    ) {
        fun activate() {
            liveData.addObserver(onChange)
        }

        fun deactivate() {
            liveData.removeObserver(onChange)
        }
    }
}
