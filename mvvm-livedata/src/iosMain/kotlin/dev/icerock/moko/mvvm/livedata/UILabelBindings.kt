/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.utils.bind
import dev.icerock.moko.resources.desc.StringDesc
import platform.UIKit.UILabel

fun <T : String?> LiveData<T>.bindStringToLabelText(
    label: UILabel
): Closeable {
    return bind(label) { this.text = it }
}

fun <T : StringDesc?> LiveData<T>.bindStringDescToLabelText(
    label: UILabel
): Closeable {
    return map { it?.localized() }.bindStringToLabelText(label)
}
