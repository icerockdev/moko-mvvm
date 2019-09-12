/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.app.sample2

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.icerockdev.app.BR
import com.icerockdev.app.R
import com.icerockdev.app.databinding.ActivityEventsBinding
import com.icerockdev.library.sample2.EventsViewModel
import dev.icerock.moko.mvvm.MvvmActivity
import dev.icerock.moko.mvvm.createViewModelFactory
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain

class EventsActivity : MvvmActivity<ActivityEventsBinding, EventsViewModel>(),
    EventsViewModel.EventsListener {
    override val layoutId: Int = R.layout.activity_events
    override val viewModelVariableId: Int = BR.viewModel
    override val viewModelClass: Class<EventsViewModel> = EventsViewModel::class.java

    override fun viewModelFactory(): ViewModelProvider.Factory {
        return createViewModelFactory { EventsViewModel(eventsDispatcherOnMain()) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.eventsDispatcher.bind(
            lifecycleOwner = this,
            listener = this
        )
    }

    override fun routeToMainPage() {
        Toast.makeText(this, "here must be routing to main page", Toast.LENGTH_SHORT).show()
    }
}
