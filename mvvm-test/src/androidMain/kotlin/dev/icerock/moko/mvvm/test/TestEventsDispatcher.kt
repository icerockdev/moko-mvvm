/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.test

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher

actual fun <T : Any> createTestEventsDispatcher(): EventsDispatcher<T> {
    return EventsDispatcher { it.run() }
}
