/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner

/**
 * Created by Aleksey Mikhailov <Aleksey.Mikhailov@icerockdev.com> on 27.03.2018.
 */
abstract class MvvmEventsFragment<DB : ViewDataBinding, VM, Listener : Any> :
    MvvmFragment<DB, VM>() where VM : ViewModel, VM : EventsDispatcherOwner<Listener> {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("UNCHECKED_CAST")
        viewModel.eventsDispatcher.bind(this, this as Listener)
    }
}
