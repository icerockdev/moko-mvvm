/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.library.sample2

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.viewmodel.ViewModel

class EventsViewModel(
    val eventsDispatcher: EventsDispatcher<EventsListener>
) : ViewModel() {

    fun onButtonPressed() {
        eventsDispatcher.dispatchEvent { routeToMainPage() }
    }

    interface EventsListener {
        fun routeToMainPage()
    }
}
