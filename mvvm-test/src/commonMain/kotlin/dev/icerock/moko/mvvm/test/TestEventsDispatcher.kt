/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.test

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher

expect fun <T : Any> createTestEventsDispatcher(): EventsDispatcher<T>

expect fun <T : Any> createTestEventsDispatcher(listener: T): EventsDispatcher<T>
