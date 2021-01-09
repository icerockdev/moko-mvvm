/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.binding

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import platform.UIKit.UIControlEventValueChanged
import platform.UIKit.UISwitch
import dev.icerock.moko.mvvm.utils.bind
import dev.icerock.moko.mvvm.utils.setEventHandler

fun UISwitch.bindValue(liveData: LiveData<Boolean>) {
    liveData.bind(this) { value ->
        on = value
    }
}

fun UISwitch.bindValueTwoWay(liveData: MutableLiveData<Boolean>) {
    bindValue(liveData)
    setEventHandler(UIControlEventValueChanged) {
        if (liveData.value == on) return@setEventHandler

        liveData.value = on
    }
}
