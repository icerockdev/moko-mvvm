/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.flow.binding

import dev.icerock.moko.mvvm.flow.CMutableStateFlow
import dev.icerock.moko.mvvm.flow.CStateFlow
import dev.icerock.moko.mvvm.flow.DisposableHandle
import dev.icerock.moko.mvvm.flow.plus
import platform.UIKit.UIControlEventEditingChanged
import platform.UIKit.UITextField

fun <T : String?> UITextField.bindText(
    flow: CStateFlow<T>
): DisposableHandle {
    return bind(flow) { value ->
        if (this.text == value) return@bind

        this.text = value
    }
}

fun UITextField.bindTextTwoWay(
    flow: CMutableStateFlow<String>
): DisposableHandle {
    val readCloseable = bindText(flow)

    val writeCloseable = setEventHandler(UIControlEventEditingChanged) {
        val newText = this.text.orEmpty()

        if (flow.value == newText) return@setEventHandler

        flow.value = newText
    }

    return readCloseable + writeCloseable
}
