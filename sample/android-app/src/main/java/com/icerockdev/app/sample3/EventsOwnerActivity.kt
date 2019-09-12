/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.app.sample3

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.icerockdev.app.BR
import com.icerockdev.app.R
import com.icerockdev.app.databinding.ActivityEventsOwnerBinding
import com.icerockdev.library.sample3.EventsOwnerViewModel
import dev.icerock.moko.mvvm.MvvmEventsActivity
import dev.icerock.moko.mvvm.createViewModelFactory
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain

class EventsOwnerActivity :
    MvvmEventsActivity<ActivityEventsOwnerBinding, EventsOwnerViewModel, EventsOwnerViewModel.EventsListener>(),
    EventsOwnerViewModel.EventsListener {

    override val layoutId: Int = R.layout.activity_events_owner
    override val viewModelVariableId: Int = BR.viewModel
    override val viewModelClass: Class<EventsOwnerViewModel> = EventsOwnerViewModel::class.java

    override fun viewModelFactory(): ViewModelProvider.Factory {
        return createViewModelFactory { EventsOwnerViewModel(eventsDispatcherOnMain()) }
    }

    override fun routeToMainPage() {
        Toast.makeText(this, "here must be routing to main page", Toast.LENGTH_SHORT).show()
    }
}