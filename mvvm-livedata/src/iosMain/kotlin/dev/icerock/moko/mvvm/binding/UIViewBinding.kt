/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.binding

import dev.icerock.moko.mvvm.livedata.LiveData
import platform.UIKit.UIView
import platform.UIKit.hidden
import dev.icerock.moko.mvvm.utils.bind

fun UIView.bindVisibility(
    liveData: LiveData<Boolean>,
    inverted: Boolean = false
) {
    liveData.bind(this) { value ->
        hidden = if (inverted) value else !value
    }
}
