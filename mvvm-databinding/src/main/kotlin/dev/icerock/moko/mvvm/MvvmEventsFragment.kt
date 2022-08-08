/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.viewmodel.ViewModel

abstract class MvvmEventsFragment<DB : ViewDataBinding, VM, Listener : Any> :
    MvvmFragment<DB, VM>() where VM : ViewModel, VM : EventsDispatcherOwner<Listener> {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        @Suppress("UNCHECKED_CAST")
        viewModel.eventsDispatcher.bind(viewLifecycleOwner, this as Listener)
    }
}
