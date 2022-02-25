/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch")

package dev.icerock.moko.mvvm.livedata

import platform.UIKit.UIControl

@Deprecated(
    "Use UIControl.bindEnabled",
    replaceWith = ReplaceWith("UIControl.bindEnabled")
)
fun LiveData<Boolean>.bindBoolToControlEnabled(
    control: UIControl
): Closeable {
    return control.bindEnabled(this)
}

@Deprecated(
    "Use UIControl.bindFocusTwoWay",
    replaceWith = ReplaceWith("UIControl.bindFocusTwoWay")
)
fun MutableLiveData<Boolean>.bindBoolTwoWayToControlFocus(control: UIControl): Closeable {
    return control.bindFocusTwoWay(this)
}
