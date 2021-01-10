/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

fun <T, OT> LiveData<T>.map(function: (T) -> OT): LiveData<OT> {
    val mutableLiveData = MutableLiveData(function(value))
    addObserver { mutableLiveData.value = function(it) }
    return mutableLiveData
}

fun <T, OT> LiveData<T>.flatMap(function: (T) -> LiveData<OT>): LiveData<OT> {
    var shadowLiveData: LiveData<OT>? = null
    var mutableLiveData: MutableLiveData<OT>? = null

    val shadowObserver: (OT) -> Unit = {
        mutableLiveData!!.value = it
    }

    addObserver { newValue ->
        shadowLiveData?.removeObserver(shadowObserver)

        val newShadowLiveData = function(newValue)
        shadowLiveData = newShadowLiveData

        if (mutableLiveData == null) {
            mutableLiveData = MutableLiveData(newShadowLiveData.value)
        }

        newShadowLiveData.addObserver(shadowObserver)
    }

    return mutableLiveData!!
}

fun <OT, I1T, I2T> LiveData<I1T>.mergeWith(
    secondLiveData: LiveData<I2T>,
    function: (I1T, I2T) -> OT
): MediatorLiveData<OT> {
    return MediatorLiveData(function(value, secondLiveData.value))
        .compose(this, secondLiveData, function)
}

fun <T, OT> LiveData<T>.mapBuffered(function: (current: T, new: T) -> OT): LiveData<OT> {
    var current = value
    return map { newValue ->
        val result = function(current, newValue)
        current = newValue
        result
    }
}

fun <T, OT> LiveData<T>.flatMapBuffered(function: (current: T, new: T) -> LiveData<OT>): LiveData<OT> {
    var current = value
    return flatMap { newValue ->
        val result = function(current, newValue)
        current = newValue
        result
    }
}
