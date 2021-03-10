/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.utils.bind
import dev.icerock.moko.mvvm.utils.setEventHandler
import dev.icerock.moko.resources.desc.StringDesc
import platform.Foundation.NSNotificationCenter
import platform.UIKit.UITextView
import platform.UIKit.UITextViewTextDidBeginEditingNotification
import platform.UIKit.UITextViewTextDidChangeNotification
import platform.UIKit.UITextViewTextDidEndEditingNotification

fun <T : String?> LiveData<T>.bindStringToTextViewText(
    textView: UITextView
): Closeable {
    return bind(textView) { value ->
        if (this.text == value) return@bind

        this.text = value.orEmpty()
    }
}

fun <T : StringDesc?> LiveData<T>.bindStringDescToTextViewText(
    textView: UITextView
): Closeable {
    return map { it?.localized() }.bindStringToTextViewText(textView)
}

fun MutableLiveData<String>.bindStringTwoWayToTextViewText(
    textView: UITextView
): Closeable {
    val readCloseable = bindStringToTextViewText(textView)

    val writeCloseable = NSNotificationCenter.defaultCenter.setEventHandler(
        notification = UITextViewTextDidChangeNotification,
        ref = textView
    ) {
        val newText = this.text

        if (value == newText) return@setEventHandler

        value = newText
    }

    return readCloseable + writeCloseable
}

fun MutableLiveData<Boolean>.bindBoolTwoWayToTextViewFocus(
    textView: UITextView
): Closeable {
    val readCloseable = bindBoolToViewFocus(view = textView)

    val handler: UITextView.() -> Unit = {
        val focused = isFocused()

        if (value != focused) value = focused
    }

    val beginCloseable = NSNotificationCenter.defaultCenter.setEventHandler(
        notification = UITextViewTextDidBeginEditingNotification,
        ref = textView,
        lambda = handler
    )
    val endCloseable = NSNotificationCenter.defaultCenter.setEventHandler(
        notification = UITextViewTextDidEndEditingNotification,
        ref = textView,
        lambda = handler
    )

    return readCloseable + beginCloseable + endCloseable
}
