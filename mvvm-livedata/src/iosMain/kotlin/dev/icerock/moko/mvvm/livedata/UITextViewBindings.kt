/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.utils.bind
import dev.icerock.moko.mvvm.utils.setEventHandler
import platform.Foundation.NSNotificationCenter
import platform.UIKit.UITextView
import platform.UIKit.UITextViewTextDidBeginEditingNotification
import platform.UIKit.UITextViewTextDidChangeNotification
import platform.UIKit.UITextViewTextDidEndEditingNotification

fun <T : String?> UITextView.bindText(
    liveData: LiveData<T>
): Closeable {
    return bind(liveData) { value ->
        if (this.text == value) return@bind

        this.text = value.orEmpty()
    }
}

fun UITextView.bindTextTwoWay(
    liveData: MutableLiveData<String>
): Closeable {
    val readCloseable = bindText(liveData)

    val writeCloseable = NSNotificationCenter.defaultCenter.setEventHandler(
        notification = UITextViewTextDidChangeNotification,
        ref = this
    ) {
        val newText = this.text

        if (liveData.value == newText) return@setEventHandler

        liveData.value = newText
    }

    return readCloseable + writeCloseable
}

fun UITextView.bindFocusTwoWay(
    liveData: MutableLiveData<Boolean>
): Closeable {
    val readCloseable = bindFocus(liveData)

    val handler: UITextView.() -> Unit = {
        val focused = isFocused()

        if (liveData.value != focused) liveData.value = focused
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
