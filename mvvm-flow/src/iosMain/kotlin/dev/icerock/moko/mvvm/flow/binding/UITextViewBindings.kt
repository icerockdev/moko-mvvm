/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.flow.binding

import dev.icerock.moko.mvvm.flow.CMutableStateFlow
import dev.icerock.moko.mvvm.flow.CStateFlow
import dev.icerock.moko.mvvm.flow.DisposableHandle
import dev.icerock.moko.mvvm.flow.plus
import dev.icerock.moko.mvvm.flow.binding.setEventHandler
import platform.Foundation.NSNotificationCenter
import platform.UIKit.UITextView
import platform.UIKit.UITextViewTextDidBeginEditingNotification
import platform.UIKit.UITextViewTextDidChangeNotification
import platform.UIKit.UITextViewTextDidEndEditingNotification

fun <T : String?> UITextView.bindText(
    flow: CStateFlow<T>
): DisposableHandle {
    return bind(flow) { value ->
        if (this.text == value) return@bind

        this.text = value.orEmpty()
    }
}

fun UITextView.bindTextTwoWay(
    flow: CMutableStateFlow<String>
): DisposableHandle {
    val readCloseable = bindText(flow)

    val writeCloseable = NSNotificationCenter.defaultCenter.setEventHandler(
        notification = UITextViewTextDidChangeNotification,
        ref = this
    ) {
        val newText = this.text

        if (flow.value == newText) return@setEventHandler

        flow.value = newText
    }

    return readCloseable + writeCloseable
}

fun UITextView.bindFocusTwoWay(
    flow: CMutableStateFlow<Boolean>
): DisposableHandle {
    val readCloseable = bindFocus(flow)

    val handler: UITextView.() -> Unit = {
        val focused = isFocused()

        if (flow.value != focused) flow.value = focused
    }

    val beginCloseable = NSNotificationCenter.defaultCenter.setEventHandler(
        notification = UITextViewTextDidBeginEditingNotification,
        ref = this,
        lambda = handler
    )
    val endCloseable = NSNotificationCenter.defaultCenter.setEventHandler(
        notification = UITextViewTextDidEndEditingNotification,
        ref = this,
        lambda = handler
    )

    return readCloseable + beginCloseable + endCloseable
}
