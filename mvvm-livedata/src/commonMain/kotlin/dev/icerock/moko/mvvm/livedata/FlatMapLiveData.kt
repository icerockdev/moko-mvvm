/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

private class FlatMapLiveData<T, OT> private constructor(
    private val function: (T) -> LiveData<OT>,
    private val source: LiveData<T>,
    initialShadowLiveData: LiveData<OT>
) : MutableLiveData<OT>(initialValue = initialShadowLiveData.value) {
    private var shadowLiveData: LiveData<OT> = initialShadowLiveData

    private val observer: (T) -> Unit = { newValue ->
        shadowLiveData.removeObserver(shadowObserver)

        shadowLiveData = function(newValue)
        shadowLiveData.addObserver(shadowObserver)
    }
    private val shadowObserver: (OT) -> Unit = { this.value = it }

    constructor(
        function: (T) -> LiveData<OT>,
        source: LiveData<T>
    ) : this(function, source, function(source.value))

    override var value: OT
        get() {
            return if (isActive) super.value
            else shadowLiveData.value
        }
        set(value) {
            super.value = value
        }

    override fun onActive() {
        source.addObserver(observer)
    }

    override fun onInactive() {
        shadowLiveData.removeObserver(shadowObserver)
        source.removeObserver(observer)
    }
}

fun <T, OT> LiveData<T>.flatMap(function: (T) -> LiveData<OT>): LiveData<OT> {
    return FlatMapLiveData(
        function = function,
        source = this
    )
}
