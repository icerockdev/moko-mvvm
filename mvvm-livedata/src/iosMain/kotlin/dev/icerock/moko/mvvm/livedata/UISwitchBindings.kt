/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.utils.bind
import dev.icerock.moko.mvvm.utils.setEventHandler
import platform.UIKit.UIControlEventValueChanged
import platform.UIKit.UISwitch

fun UISwitch.bindBoolToSwitchOn(
    liveData: LiveData<Boolean>
): Closeable {
    return bind(liveData) { this.on = it }
}

fun UISwitch.bindBoolTwoWayToSwitchOn(
    liveData: MutableLiveData<Boolean>
): Closeable {
    val readCloseable = bindBoolToSwitchOn(liveData)

    val writeCloseable = setEventHandler(UIControlEventValueChanged) {
        if (liveData.value == on) return@setEventHandler

        liveData.value = on
    }

    return readCloseable + writeCloseable
}
