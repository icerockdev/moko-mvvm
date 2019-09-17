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
    var shadowLiveData = function(value)
    val mutableLiveData = MutableLiveData<OT>(shadowLiveData.value)

    val shadowObserver: (OT) -> Unit = { mutableLiveData.value = it }

    addObserver { newValue ->
        shadowLiveData.removeObserver(shadowObserver)

        shadowLiveData = function(newValue)

        mutableLiveData.value = shadowLiveData.value

        shadowLiveData.addObserver(shadowObserver)
    }

    shadowLiveData.addObserver(shadowObserver)

    return mutableLiveData
}

fun <OT, I1T, I2T> LiveData<I1T>.mergeWith(
    secondLiveData: LiveData<I2T>,
    function: (I1T, I2T) -> OT
): MediatorLiveData<OT> {
    return MediatorLiveData(function(value, secondLiveData.value))
        .compose(this, secondLiveData, function)
}

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
