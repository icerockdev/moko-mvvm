/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch")

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.livedata.resources.bindText
import dev.icerock.moko.resources.desc.StringDesc
import platform.UIKit.UITextView

@Deprecated(
    "Use UITextView.bindText",
    replaceWith = ReplaceWith("UITextView.bindText")
)
fun <T : StringDesc?> LiveData<T>.bindStringDescToTextViewText(
    textView: UITextView
): Closeable {
    return textView.bindText(this)
}
