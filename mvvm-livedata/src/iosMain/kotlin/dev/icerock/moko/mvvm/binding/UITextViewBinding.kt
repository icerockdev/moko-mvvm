/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.binding

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.bindBoolToViewFocus
import dev.icerock.moko.mvvm.livedata.bindStringToTextViewText
import dev.icerock.moko.mvvm.livedata.bindBoolTwoWayToTextViewFocus
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.mvvm.utils.setEventHandler
import dev.icerock.moko.resources.desc.StringDesc
import platform.Foundation.NSNotificationCenter
import platform.UIKit.UITextView
import platform.UIKit.UITextViewTextDidChangeNotification

@Deprecated("use LiveData.bindToTextViewText extension")
fun UITextView.bindText(
    liveData: LiveData<String>,
    formatter: ((String) -> String)? = null
) {
    liveData.map { formatter?.invoke(it) ?: it }.bindStringToTextViewText(textView = this)
}

@Deprecated("use LiveData.bindToTextViewText extension")
fun UITextView.bindText(
    liveData: LiveData<StringDesc>,
    formatter: ((String) -> String)? = null
) {
    liveData
        .map { it.localized() }
        .map { formatter?.invoke(it) ?: it }
        .bindStringToTextViewText(textView = this)
}

@Deprecated("use LiveData.bindToTextViewText extension")
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

@Deprecated("use LiveData.bindToResponderFocus extension")
fun UITextView.bindFocus(liveData: LiveData<Boolean>) {
    liveData.bindBoolToViewFocus(view = this)
}

@Deprecated("use LiveData.bindTwoWayToTextViewFocus extension")
fun UITextView.bindFocusTwoWay(liveData: MutableLiveData<Boolean>) {
    liveData.bindBoolTwoWayToTextViewFocus(textView = this)
}
