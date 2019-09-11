/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.library

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.mvvm.livedata.readOnly
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc

class TestViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>
) : ViewModel(), EventsDispatcherOwner<TestViewModel.EventsListener> {

    private val _counter: MutableLiveData<Int> = MutableLiveData(0)

    val string: LiveData<String> = _counter.map { it.toString() }
    val stringDesc: LiveData<StringDesc> = string.map { it.desc() }

    val mutableSwitch: MutableLiveData<Boolean> = MutableLiveData(false)
    val readOnlySwitch: LiveData<Boolean> = mutableSwitch.readOnly()

    val mutableString: MutableLiveData<String> = MutableLiveData("")

    fun onCounterButtonPressed() {
        val current = _counter.value
        _counter.value = current + 1
    }

    fun onErrorButtonPressed() {
        val error = "something wrong!".desc()
        eventsDispatcher.dispatchEvent { showError(error) }
    }

    interface EventsListener {
        fun showError(error: StringDesc)
    }
}
