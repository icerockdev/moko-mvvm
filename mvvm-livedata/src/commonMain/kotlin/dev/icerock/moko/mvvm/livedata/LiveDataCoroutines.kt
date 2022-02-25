/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

fun <T> LiveData<T>.asFlow(): Flow<T> = channelFlow {
    val observer: (T) -> Unit = { trySend(it) }

    addObserver(observer)

    awaitClose { removeObserver(observer) }
}

fun <T> Flow<T>.asLiveData(coroutineScope: CoroutineScope, initialValue: T): LiveData<T> {
    val resultLiveData = MutableLiveData(initialValue)
    coroutineScope.launch {
        runCatching {
            collect {
                resultLiveData.value = it
            }
        }
    }
    return resultLiveData
}

fun <T> StateFlow<T>.asLiveData(coroutineScope: CoroutineScope): LiveData<T> {
    return asLiveData(coroutineScope, value)
}
