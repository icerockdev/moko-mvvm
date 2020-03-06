/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.dispatcher

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import java.util.concurrent.Executor

actual class EventsDispatcher<ListenerType : Any> {
    private var eventsListener: ListenerType? = null
    private val blocks = mutableListOf<ListenerType.() -> Unit>()
    private val executor: Executor

    actual constructor() {
        this.executor = createExecutorOnMainLooper()
    }

    constructor(executor: Executor) {
        this.executor = executor
    }

    fun bind(lifecycleOwner: LifecycleOwner, listener: ListenerType) {
        val observer = object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun connectListener() {
                eventsListener = listener
                executor.execute {
                    blocks.forEach { it(listener) }
                    blocks.clear()
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun disconnectListener() {
                eventsListener = null
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroyed(source: LifecycleOwner) {
                source.lifecycle.removeObserver(this)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
    }

    actual fun dispatchEvent(block: ListenerType.() -> Unit) {
        val eListener = eventsListener
        if (eListener != null) {
            executor.execute { block(eListener) }
        } else {
            executor.execute { blocks.add(block) }
        }
    }

    private fun createExecutorOnMainLooper(): Executor {
        val mainLooper = Looper.getMainLooper()
        val mainHandler = Handler(mainLooper)
        return Executor { mainHandler.post(it) }
    }
}
