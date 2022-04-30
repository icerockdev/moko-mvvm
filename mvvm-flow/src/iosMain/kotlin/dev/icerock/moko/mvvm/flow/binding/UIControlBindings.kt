/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.flow.binding

import dev.icerock.moko.mvvm.flow.CMutableStateFlow
import dev.icerock.moko.mvvm.flow.CStateFlow
import dev.icerock.moko.mvvm.flow.DisposableHandle
import dev.icerock.moko.mvvm.flow.plus
import platform.UIKit.UIControl
import platform.UIKit.UIControlEventEditingDidBegin
import platform.UIKit.UIControlEventEditingDidEnd
import platform.UIKit.UIControlEventEditingDidEndOnExit

fun UIControl.bindEnabled(
    flow: CStateFlow<Boolean>
): DisposableHandle {
    return bind(flow) { this.enabled = it }
}

fun UIControl.bindFocusTwoWay(flow: CMutableStateFlow<Boolean>): DisposableHandle {
    val readCloseable = bindFocus(flow)

    val handler: UIControl.() -> Unit = {
        val focused = isFocused()
        if (flow.value != focused) flow.value = focused
    }

    val beginCloseable = setEventHandler(UIControlEventEditingDidBegin, handler)
    val endCloseable = setEventHandler(UIControlEventEditingDidEnd, handler)
    val endOnExitCloseable = setEventHandler(UIControlEventEditingDidEndOnExit, handler)

    return readCloseable + beginCloseable + endCloseable + endOnExitCloseable
}
