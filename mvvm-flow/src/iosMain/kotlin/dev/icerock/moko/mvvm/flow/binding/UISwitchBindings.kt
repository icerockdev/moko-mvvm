/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.flow.binding

import dev.icerock.moko.mvvm.flow.CMutableStateFlow
import dev.icerock.moko.mvvm.flow.CStateFlow
import dev.icerock.moko.mvvm.flow.DisposableHandle
import dev.icerock.moko.mvvm.flow.plus
import platform.UIKit.UIControlEventValueChanged
import platform.UIKit.UISwitch

fun UISwitch.bindSwitchOn(
    flow: CStateFlow<Boolean>
): DisposableHandle {
    return bind(flow) { this.on = it }
}

fun UISwitch.bindSwitchOnTwoWay(
    flow: CMutableStateFlow<Boolean>
): DisposableHandle {
    val readCloseable = bindSwitchOn(flow)

    val writeCloseable = setEventHandler(UIControlEventValueChanged) {
        if (flow.value == on) return@setEventHandler

        flow.value = on
    }

    return readCloseable + writeCloseable
}
