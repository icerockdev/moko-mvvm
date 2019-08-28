/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.core.Timer

class DebounceLiveData<T>(private val liveData: LiveData<T?>, timer: Long) {
    private var lastValue: T? = null

    private val timer = Timer(timer) {
        _output.value = lastValue
        false
    }

    private val _output = MutableLiveData<T?>(null)
    val output = _output.readOnly()

    private val observer: (T?) -> Unit = {
        if (lastValue == null && it != null) {
            _output.value = it
        } else {
            this.timer.apply {
                stop()
                start()
            }
        }

        lastValue = it
    }

    init {
        liveData.addObserver(observer)
    }

    fun stopTimer() {
        timer.stop()
        liveData.removeObserver(observer)
    }
}

fun <T> LiveData<T?>.debounce(timeInMillis: Long) = DebounceLiveData(this, timeInMillis)