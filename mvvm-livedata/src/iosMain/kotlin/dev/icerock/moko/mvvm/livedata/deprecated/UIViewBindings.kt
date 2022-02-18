/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch")

package dev.icerock.moko.mvvm.livedata

import platform.UIKit.UIColor
import platform.UIKit.UIView

@Deprecated(
    "Use UIView.bindBackgroundColor",
    replaceWith = ReplaceWith("UIView.bindBackgroundColor")
)
fun LiveData<Boolean>.bindBoolToViewBackgroundColor(
    view: UIView,
    trueColor: UIColor,
    falseColor: UIColor
): Closeable {
    return view.bindBackgroundColor(this, trueColor, falseColor)
}

@Deprecated(
    "Use UIView.bindHidden",
    replaceWith = ReplaceWith("UIView.bindHidden")
)
fun LiveData<Boolean>.bindBoolToViewHidden(
    view: UIView
): Closeable {
    return view.bindHidden(this)
}

@Deprecated(
    "Use UIView.bindFocus",
    replaceWith = ReplaceWith("UIView.bindFocus")
)
fun LiveData<Boolean>.bindBoolToViewFocus(view: UIView): Closeable {
    return view.bindFocus(this)
}
