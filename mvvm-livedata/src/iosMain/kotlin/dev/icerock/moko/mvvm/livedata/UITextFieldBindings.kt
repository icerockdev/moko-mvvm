/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.utils.bind
import dev.icerock.moko.mvvm.utils.setEventHandler
import platform.UIKit.UIControlEventEditingChanged
import platform.UIKit.UITextField

fun <T : String?> UITextField.bindText(
    liveData: LiveData<T>
): Closeable {
    return bind(liveData) { value ->
        if (this.text == value) return@bind

        this.text = value
    }
}

fun UITextField.bindTextTwoWay(
    liveData: MutableLiveData<String>
): Closeable {
    val readCloseable = bindText(liveData)

    val writeCloseable = setEventHandler(UIControlEventEditingChanged) {
        val newText = this.text.orEmpty()

        if (liveData.value == newText) return@setEventHandler

        liveData.value = newText
    }

    return readCloseable + writeCloseable
}
