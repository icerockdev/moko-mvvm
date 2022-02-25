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

fun UIControl.bindEnabled(
    liveData: LiveData<Boolean>
): Closeable {
    return bind(liveData) { this.enabled = it }
}

fun UIControl.bindFocusTwoWay(liveData: MutableLiveData<Boolean>): Closeable {
    val readCloseable = bindFocus(liveData)

    val handler: UIControl.() -> Unit = {
        val focused = isFocused()
        if (liveData.value != focused) liveData.value = focused
    }

    val beginCloseable = setEventHandler(UIControlEventEditingDidBegin, handler)
    val endCloseable = setEventHandler(UIControlEventEditingDidEnd, handler)
    val endOnExitCloseable = setEventHandler(UIControlEventEditingDidEndOnExit, handler)

    return readCloseable + beginCloseable + endCloseable + endOnExitCloseable
}
