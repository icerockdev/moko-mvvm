/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.dispatcher

import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import kotlin.native.ref.WeakReference

actual class EventsDispatcher<ListenerType : Any>(listener: ListenerType) {
    private val weakListener: WeakReference<ListenerType> = WeakReference(listener)

    actual fun dispatchEvent(block: ListenerType.() -> Unit) {
        weakListener.get()?.let {
            val mainQueue = dispatch_get_main_queue()
            dispatch_async(mainQueue) {
                block(it)
            }
        }
    }
}
