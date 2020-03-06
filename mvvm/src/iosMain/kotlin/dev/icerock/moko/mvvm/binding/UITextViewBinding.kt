/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.binding

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.mvvm.utils.bind
import dev.icerock.moko.mvvm.utils.setEventHandler
import dev.icerock.moko.resources.desc.StringDesc
import platform.Foundation.NSNotificationCenter
import platform.UIKit.UITextView
import platform.UIKit.UITextViewTextDidBeginEditingNotification
import platform.UIKit.UITextViewTextDidChangeNotification
import platform.UIKit.UITextViewTextDidEndEditingNotification

fun UITextView.bindText(
    liveData: LiveData<String>,
    formatter: ((String) -> String)? = null
) {
    liveData.bind(this) { value ->
        val newText = formatter?.invoke(value) ?: value
        if (text == newText) return@bind
        text = newText
    }
}

fun UITextView.bindText(
    liveData: LiveData<StringDesc>,
    formatter: ((String) -> String)? = null
) {
    bindText(
        liveData = liveData.map { it.localized() },
        formatter = formatter
    )
}

fun UITextView.bindTextTwoWay(
    liveData: MutableLiveData<String>,
    formatter: ((String) -> String)? = null,
    reverseFormatter: ((String) -> String)? = null
) {
    bindText(liveData, formatter)

    NSNotificationCenter.defaultCenter.setEventHandler(
        notification = UITextViewTextDidChangeNotification,
        ref = this
    ) {
        val newText = this.text
        val newFormattedText = reverseFormatter?.invoke(newText) ?: newText

        if (liveData.value == newFormattedText) return@setEventHandler

        liveData.value = newFormattedText
    }
}

fun UITextView.bindFocus(liveData: LiveData<Boolean>) {
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

fun UITextView.bindFocusTwoWay(liveData: MutableLiveData<Boolean>) {
    bindFocus(liveData)
    val handler: UITextView.() -> Unit = {
        val focused = isFocused()
        if (liveData.value != focused) liveData.value = focused
    }

    NSNotificationCenter.defaultCenter.setEventHandler(
        notification = UITextViewTextDidBeginEditingNotification,
        ref = this,
        lambda = handler
    )
    NSNotificationCenter.defaultCenter.setEventHandler(
        notification = UITextViewTextDidEndEditingNotification,
        ref = this,
        lambda = handler
    )
}
