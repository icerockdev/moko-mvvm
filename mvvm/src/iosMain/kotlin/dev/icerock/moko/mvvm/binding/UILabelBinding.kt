/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.binding

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.resources.desc.StringDesc
import platform.UIKit.UILabel
import dev.icerock.moko.mvvm.utils.bind

fun UILabel.bindText(
    liveData: LiveData<String>,
    formatter: ((String) -> String) = { it }
) {
    liveData.bind(this) { value ->
        val newText = formatter(value)

        if (newText == text) return@bind

        text = newText
    }
}

fun UILabel.bindText(
    liveData: LiveData<StringDesc>,
    formatter: ((String) -> String) = { it }
) {
    bindText(
        liveData = liveData.map { it.localized() },
        formatter = formatter
    )
}
