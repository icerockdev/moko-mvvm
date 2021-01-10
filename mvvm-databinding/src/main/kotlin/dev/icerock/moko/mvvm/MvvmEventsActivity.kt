/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.viewmodel.ViewModel

abstract class MvvmEventsActivity<DB : ViewDataBinding, VM, Listener : Any> :
    MvvmActivity<DB, VM>() where VM : ViewModel, VM : EventsDispatcherOwner<Listener> {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("UNCHECKED_CAST")
        viewModel.eventsDispatcher.bind(this, this as Listener)
    }
}
