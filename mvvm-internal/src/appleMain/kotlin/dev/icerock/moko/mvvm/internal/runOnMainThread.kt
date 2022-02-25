/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.internal

import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_current_queue
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_queue_main_t

actual fun runOnMainThread(block: () -> Unit) {
    val mainQueue: dispatch_queue_main_t = dispatch_get_main_queue()
    if (dispatch_get_current_queue() == mainQueue) {
        block()
    } else {
        dispatch_async(mainQueue) { block() }
    }
}
