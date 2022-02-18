/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch")

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.livedata.resources.bindText
import dev.icerock.moko.resources.desc.StringDesc
import platform.UIKit.UILabel

@Deprecated(
    "Use UILabel.bindText",
    replaceWith = ReplaceWith("UILabel.bindText")
)
fun <T : StringDesc?> LiveData<T>.bindStringDescToLabelText(
    label: UILabel
): Closeable {
    return label.bindText(this)
}
