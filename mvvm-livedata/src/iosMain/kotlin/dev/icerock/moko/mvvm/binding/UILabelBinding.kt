/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.binding

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.bindToLabelText
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.resources.desc.StringDesc
import platform.UIKit.UILabel

@Deprecated("use LiveData.bindToLabelText extension")
fun UILabel.bindText(
    liveData: LiveData<String>,
    formatter: ((String) -> String) = { it }
) {
    liveData.map(formatter).bindToLabelText(label = this)
}

@Deprecated("use LiveData.bindToLabelText extension")
fun UILabel.bindText(
    liveData: LiveData<StringDesc>,
    formatter: ((String) -> String) = { it }
) {
    liveData.map { it.localized() }.map(formatter).bindToLabelText(label = this)
}
