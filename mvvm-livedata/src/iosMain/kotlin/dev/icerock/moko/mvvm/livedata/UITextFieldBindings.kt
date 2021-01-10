/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.utils.bind
import dev.icerock.moko.mvvm.utils.setEventHandler
import dev.icerock.moko.resources.desc.StringDesc
import platform.UIKit.UIControlEventEditingChanged
import platform.UIKit.UITextField

fun LiveData<String>.bindToTextFieldText(
    textField: UITextField
) {
    bind(textField) { value ->
        if (this.text == value) return@bind

        this.text = value
    }
}

fun LiveData<StringDesc>.bindToTextFieldText(
    textField: UITextField
) {
    map { it.localized() }.bindToTextFieldText(textField)
}

fun MutableLiveData<String>.bindTwoWayToTextFieldText(
    textField: UITextField
) {
    bindToTextFieldText(textField)

    textField.setEventHandler(UIControlEventEditingChanged) {
        val newText = this.text.orEmpty()

        if (value == newText) return@setEventHandler

        value = newText
    }
}
