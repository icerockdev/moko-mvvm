/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.utils.bind
import dev.icerock.moko.resources.desc.StringDesc
import platform.UIKit.UILabel

fun LiveData<String>.bindToLabelText(
    label: UILabel
) {
    bind(label) { this.text = it }
}

fun LiveData<StringDesc>.bindToLabelText(
    label: UILabel
) {
    map { it.localized() }.bindToLabelText(label)
}
