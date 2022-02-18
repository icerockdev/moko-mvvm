/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch")

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.livedata.resources.bindTitle
import dev.icerock.moko.resources.desc.StringDesc
import platform.UIKit.UIButton

@Deprecated(
    "Use UIButton.bindTitle",
    replaceWith = ReplaceWith("UIButton.bindTitle")
)
fun <T : StringDesc?> LiveData<T>.bindStringDescToButtonTitle(button: UIButton): Closeable {
    return button.bindTitle(this)
}
