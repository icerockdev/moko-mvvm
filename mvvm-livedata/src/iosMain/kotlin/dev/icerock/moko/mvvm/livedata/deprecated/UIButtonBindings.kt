/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch")

package dev.icerock.moko.mvvm.livedata

import platform.UIKit.UIButton
import platform.UIKit.UIImage

@Deprecated(
    "Use UIButton.bindTitle",
    replaceWith = ReplaceWith("UIButton.bindTitle")
)
fun <T : String?> LiveData<T>.bindStringToButtonTitle(button: UIButton): Closeable {
    return button.bindTitle(this)
}

@Deprecated(
    "Use UIButton.bindImage",
    replaceWith = ReplaceWith("UIButton.bindImage")
)
fun LiveData<Boolean>.bindBoolToButtonImage(
    button: UIButton,
    trueImage: UIImage,
    falseImage: UIImage
): Closeable {
    return button.bindImage(this, trueImage, falseImage)
}
