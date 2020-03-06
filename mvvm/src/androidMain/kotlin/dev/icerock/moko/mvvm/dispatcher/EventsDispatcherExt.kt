/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.dispatcher

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

fun createExecutorOnMainLooper(): Executor {
    val mainLooper = Looper.getMainLooper()
    val mainHandler = Handler(mainLooper)
    return Executor { mainHandler.post(it) }
}

inline fun <reified T : Any> eventsDispatcherOnMain(): EventsDispatcher<T> {
    return EventsDispatcher(createExecutorOnMainLooper())
}
