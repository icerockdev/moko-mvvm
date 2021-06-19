/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

@Deprecated(
    message = "Use mediatorOf() instead",
    replaceWith = ReplaceWith(
        expression = "mediatorOf()",
        imports = arrayOf("dev.icerock.moko.mvvm.livedata.mediatorOf")
    )
)
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
