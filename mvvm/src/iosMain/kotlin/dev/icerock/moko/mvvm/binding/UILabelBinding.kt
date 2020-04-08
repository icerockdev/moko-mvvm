/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.binding

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.map
import platform.UIKit.UILabel
import dev.icerock.moko.mvvm.utils.bind

fun <T> UILabel.bindText(
    liveData: LiveData<T>,
    formatter: ((String) -> String) = { it }
) {
    liveData.map{ it as T? }.descOrEmpty().bind(this) { value ->
        val newText = formatter(value.localized())

        if (newText == text) return@bind

        text = newText
    }
}