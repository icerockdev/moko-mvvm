/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

@UseExperimental(ExperimentalCoroutinesApi::class)
fun <T> LiveData<T>.asFlow(): Flow<T> = channelFlow {
    val observer: (T) -> Unit = { offer(it) }

    addObserver(observer)

    awaitClose { removeObserver(observer) }
}
