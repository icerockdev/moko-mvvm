/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

actual open class CFlow<T> actual constructor(private val flow: Flow<T>) : Flow<T> by flow {

    fun subscribe(coroutineScope: CoroutineScope, onCollect: (T) -> Unit): Disposable {
        val job: Job = coroutineScope.launch(Dispatchers.Main) {
            flow.onEach { onCollect(it) }
                .collect()
        }
        return object : Disposable {
            override fun dispose() {
                job.cancel()
            }
        }
    }

    fun subscribe(onCollect: (T) -> Unit): Disposable {
        @Suppress("OPT_IN_USAGE")
        return subscribe(coroutineScope = GlobalScope, onCollect)
    }

    interface Disposable {
        fun dispose()
    }
}

actual class CStateFlow<T> actual constructor(
    private val flow: StateFlow<T>
) : CFlow<T>(flow), StateFlow<T> {
    override val replayCache: List<T> get() = flow.replayCache

    override suspend fun collect(collector: FlowCollector<T>): Nothing = flow.collect(collector)

    override val value: T get() = flow.value
}
