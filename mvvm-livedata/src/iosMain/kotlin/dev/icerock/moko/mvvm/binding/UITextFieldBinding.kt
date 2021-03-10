/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.binding

import dev.icerock.moko.mvvm.livedata.Closeable
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.bindBoolToViewFocus
import dev.icerock.moko.mvvm.livedata.bindBoolTwoWayToControlFocus
import dev.icerock.moko.mvvm.livedata.bindStringToTextFieldText
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.mvvm.utils.setEventHandler
import dev.icerock.moko.resources.desc.StringDesc
import platform.UIKit.UIControlEventEditingChanged
import platform.UIKit.UITextField

@Deprecated("use LiveData.bindToTextFieldText extension")
fun UITextField.bindText(
    liveData: LiveData<String>,
    formatter: ((String) -> String)? = null
): Closeable {
    return liveData.map { formatter?.invoke(it) ?: it }.bindStringToTextFieldText(textField = this)
}

@Deprecated("use LiveData.bindToTextFieldText extension")
fun UITextField.bindText(
    liveData: LiveData<StringDesc>,
    formatter: ((String) -> String)? = null
): Closeable {
    return liveData
        .map { it.localized() }
        .map { formatter?.invoke(it) ?: it }
        .bindStringToTextFieldText(textField = this)
}

@Deprecated("use LiveData.bindTwoWayToTextFieldText extension")
fun UITextField.bindTextTwoWay(
    liveData: MutableLiveData<String>,
    formatter: ((String) -> String)? = null,
    reverseFormatter: ((String) -> String)? = null
): Closeable {
    val readCloseable = bindText(liveData, formatter)

    val writeCloseable = setEventHandler(UIControlEventEditingChanged) {
        val newText = this.text.orEmpty()
        val newFormattedText = reverseFormatter?.invoke(newText) ?: newText

        if (liveData.value == newFormattedText) return@setEventHandler

        liveData.value = newFormattedText
    }

    return readCloseable + writeCloseable
}

@Deprecated("use LiveData.bindToControlFocus extension")
fun UITextField.bindFocus(liveData: LiveData<Boolean>): Closeable {
    return liveData.bindBoolToViewFocus(view = this)
}

@Deprecated("use LiveData.    liveData.bindTwoWayToControlFocus(control = this)\n extension")
fun UITextField.bindFocusTwoWay(liveData: MutableLiveData<Boolean>): Closeable {
    return liveData.bindBoolTwoWayToControlFocus(control = this)
}
