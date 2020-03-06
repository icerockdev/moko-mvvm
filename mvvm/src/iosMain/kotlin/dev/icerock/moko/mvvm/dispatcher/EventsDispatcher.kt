/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.dispatcher

import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_queue_t
import kotlin.native.ref.WeakReference

actual class EventsDispatcher<ListenerType : Any> {
    private var weakListener: WeakReference<ListenerType>? = null
    private val queue: dispatch_queue_t

    var listener: ListenerType?
        get() = weakListener?.get()
        set(value) {
            weakListener = if (value == null) {
                null
            } else {
                WeakReference(value)
            }
        }

    actual constructor() {
        this.queue = dispatch_get_main_queue()
    }

    constructor(queue: dispatch_queue_t) {
        this.queue = queue
    }

    constructor(listener: ListenerType) {
        this.queue = dispatch_get_main_queue()
        this.listener = listener
    }

    actual fun dispatchEvent(block: ListenerType.() -> Unit) {
        val listener = weakListener?.get() ?: return
        dispatch_async(queue) {
            block(listener)
        }
    }
}
