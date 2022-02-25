/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.dispatcher

import dev.icerock.moko.mvvm.internal.WeakReference
import dev.icerock.moko.mvvm.internal.runOnMainThread

actual class EventsDispatcher<ListenerType : Any> actual constructor() {
    private var weakListener: WeakReference<ListenerType>? = null
    private val blocks = mutableListOf<ListenerType.() -> Unit>()

    var listener: ListenerType?
        get() = weakListener?.get()
        set(value) {
            weakListener = value?.let { WeakReference(it) }
            if (value != null) {
                blocks.forEach { it.invoke(value) }
                blocks.clear()
            }
        }

    constructor(listener: ListenerType) : this() {
        this.listener = listener
    }

    actual fun dispatchEvent(block: ListenerType.() -> Unit) {
        val listener = weakListener?.get()

        if (listener == null) {
            blocks.add(block)
            return
        }

        runOnMainThread { block(listener) }
    }
}
