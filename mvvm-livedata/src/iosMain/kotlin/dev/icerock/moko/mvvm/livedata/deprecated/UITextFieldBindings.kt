/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch")

package dev.icerock.moko.mvvm.livedata

import platform.UIKit.UITextField

@Deprecated(
    "Use UITextField.bindText",
    replaceWith = ReplaceWith("UITextField.bindText")
)
fun <T : String?> LiveData<T>.bindStringToTextFieldText(
    textField: UITextField
): Closeable {
    return textField.bindText(this)
}

@Deprecated(
    "Use UITextField.bindTextTwoWay",
    replaceWith = ReplaceWith("UITextField.bindTextTwoWay")
)
fun MutableLiveData<String>.bindStringTwoWayToTextFieldText(
    textField: UITextField
): Closeable {
    return textField.bindTextTwoWay(this)
}
