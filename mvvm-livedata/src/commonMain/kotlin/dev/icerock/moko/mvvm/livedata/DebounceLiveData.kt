/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

fun <T> LiveData<T>.debounce(
    coroutineScope: CoroutineScope,
    timeInMillis: Long
): LiveData<T> {
    val resultLiveData = MutableLiveData(this.value)

    coroutineScope.launch {
        this@debounce.asFlow()
            .debounce(timeInMillis)
            .collect {
                resultLiveData.value = it
            }
    }

    return resultLiveData
}