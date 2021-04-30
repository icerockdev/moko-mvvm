/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.utils.bind
import dev.icerock.moko.mvvm.utils.setEventHandler
import platform.UIKit.UIControl
import platform.UIKit.UIControlEventEditingDidBegin
import platform.UIKit.UIControlEventEditingDidEnd
import platform.UIKit.UIControlEventEditingDidEndOnExit

fun LiveData<Boolean>.bindBoolToControlEnabled(
    control: UIControl
): Closeable {
    return bind(control) { this.enabled = it }
}

fun MutableLiveData<Boolean>.bindBoolTwoWayToControlFocus(control: UIControl): Closeable {
    val readCloseable = bindBoolToViewFocus(view = control)

    val handler: UIControl.() -> Unit = {
        val focused = isFocused()
        if (value != focused) value = focused
    }

    val beginCloseable = control.setEventHandler(UIControlEventEditingDidBegin, handler)
    val endCloseable = control.setEventHandler(UIControlEventEditingDidEnd, handler)
    val endOnExitCloseable = control.setEventHandler(UIControlEventEditingDidEndOnExit, handler)

    return readCloseable + beginCloseable + endCloseable + endOnExitCloseable
}
