/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> LiveData<T>.asFlow(): Flow<T> = channelFlow {
    val observer: (T) -> Unit = { offer(it) }

    addObserver(observer)

    awaitClose { removeObserver(observer) }
}

fun <T> Flow<T>.asLiveData(coroutineScope: CoroutineScope, initialValue: T): LiveData<T> {
    return FlowLiveData(
        flow = this,
        coroutineScope = coroutineScope,
        initialValue = initialValue
    )
}

fun <T> StateFlow<T>.asLiveData(coroutineScope: CoroutineScope): LiveData<T> {
    return asLiveData(coroutineScope, value)
}

private class FlowLiveData<T>(
    private val flow: Flow<T>,
    private val coroutineScope: CoroutineScope,
    initialValue: T
) : MutableLiveData<T>(initialValue) {
    private var activeJob: Job? = null

    override fun onActive() {
        super.onActive()

        activeJob = coroutineScope.launch {
            runCatching {
                flow.collect { this@FlowLiveData.value = it }
            }
        }
    }

    override fun onInactive() {
        super.onInactive()

        activeJob?.cancel()
        activeJob = null
    }
}
