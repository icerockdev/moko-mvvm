/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.viewbinding

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.viewmodel.ViewModel

abstract class MvvmEventsFragment<VB : ViewBinding, VM, Listener : Any> :
    MvvmFragment<VB, VM>() where VM : ViewModel, VM : EventsDispatcherOwner<Listener> {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("UNCHECKED_CAST")
        viewModel.eventsDispatcher.bind(viewLifecycleOwner, this as Listener)
    }
}
