/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */
package com.icerockdev.library.sample7

import dev.icerock.moko.mvvm.State
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.readOnly
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kotlin.random.Random

class StateSampleViewModel():  ViewModel() {
    private val _state: MutableLiveData<State<String, StringDesc>> = MutableLiveData(State.Empty())
    val state = _state.readOnly()
    private val rng = Random.Default

    fun updateState() {
        when (rng.nextInt(4)) {
            0 -> _state.postValue(State.Data("Some data"))
            1 -> _state.postValue(State.Empty())
            2 -> {
                _state.postValue(State.Loading())
                //todo: waiting a little
                this.updateState()
            }
            3 -> _state.postValue(State.Error("Some error".desc()))
        }
    }
}