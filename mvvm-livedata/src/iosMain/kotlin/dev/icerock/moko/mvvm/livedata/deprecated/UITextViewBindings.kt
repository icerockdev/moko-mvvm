/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch")

package dev.icerock.moko.mvvm.livedata

import platform.UIKit.UITextView

@Deprecated(
    "Use UITextView.bindText",
    replaceWith = ReplaceWith("UITextView.bindText")
)
fun <T : String?> LiveData<T>.bindStringToTextViewText(
    textView: UITextView
): Closeable {
    return textView.bindText(this)
}

@Deprecated(
    "Use UITextView.bindTextTwoWay",
    replaceWith = ReplaceWith("UITextView.bindTextTwoWay")
)
fun MutableLiveData<String>.bindStringTwoWayToTextViewText(
    textView: UITextView
): Closeable {
    return textView.bindTextTwoWay(this)
}

@Deprecated(
    "Use UITextView.bindFocusTwoWay",
    replaceWith = ReplaceWith("UITextView.bindFocusTwoWay")
)
fun MutableLiveData<Boolean>.bindBoolTwoWayToTextViewFocus(
    textView: UITextView
): Closeable {
    return textView.bindFocusTwoWay(this)
}
