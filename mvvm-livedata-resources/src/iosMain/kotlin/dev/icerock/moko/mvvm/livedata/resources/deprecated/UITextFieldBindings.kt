/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch")

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.livedata.resources.bindText
import dev.icerock.moko.resources.desc.StringDesc
import platform.UIKit.UITextField

@Deprecated(
    "Use UITextField.bindText",
    replaceWith = ReplaceWith("UITextField.bindText")
)
fun <T : StringDesc?> LiveData<T>.bindStringDescToTextFieldText(
    textField: UITextField
): Closeable {
    return textField.bindText(this)
}
