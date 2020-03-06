/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.binding

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.resources.desc.StringDesc
import platform.UIKit.UIControlEventEditingChanged
import platform.UIKit.UIControlEventEditingDidBegin
import platform.UIKit.UIControlEventEditingDidEnd
import platform.UIKit.UIControlEventEditingDidEndOnExit
import platform.UIKit.UITextField
import dev.icerock.moko.mvvm.utils.bind
import dev.icerock.moko.mvvm.utils.setEventHandler

fun UITextField.bindText(
    liveData: LiveData<String>,
    formatter: ((String) -> String)? = null
) {
    liveData.bind(this) { value ->
        val newText = formatter?.invoke(value) ?: value
        if (text == newText) return@bind
        text = newText
    }
}

fun UITextField.bindText(
    liveData: LiveData<StringDesc>,
    formatter: ((String) -> String)? = null
) {
    bindText(
        liveData = liveData.map { it.localized() },
        formatter = formatter
    )
}

fun UITextField.bindTextTwoWay(
    liveData: MutableLiveData<String>,
    formatter: ((String) -> String)? = null,
    reverseFormatter: ((String) -> String)? = null
) {
    bindText(liveData, formatter)

    setEventHandler(UIControlEventEditingChanged) {
        val newText = this.text.orEmpty()
        val newFormattedText = reverseFormatter?.invoke(newText) ?: newText

        if (liveData.value == newFormattedText) return@setEventHandler

        liveData.value = newFormattedText
    }
}

fun UITextField.bindFocus(liveData: LiveData<Boolean>) {
    liveData.bind(this) { value ->
        if (value) {
            becomeFirstResponder()
        } else {
            if (nextResponder?.canBecomeFirstResponder == true) {
                nextResponder?.becomeFirstResponder()
            } else {
                resignFirstResponder()
            }
        }
    }
}

fun UITextField.bindFocusTwoWay(liveData: MutableLiveData<Boolean>) {
    bindFocus(liveData)
    val handler: UITextField.() -> Unit = {
        val focused = isFocused()
        if (liveData.value != focused) liveData.value = focused
    }

    setEventHandler(UIControlEventEditingDidBegin, handler)
    setEventHandler(UIControlEventEditingDidEnd, handler)
    setEventHandler(UIControlEventEditingDidEndOnExit, handler)
}
