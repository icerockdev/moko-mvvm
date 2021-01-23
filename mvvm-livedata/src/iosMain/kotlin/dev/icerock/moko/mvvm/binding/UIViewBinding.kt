/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.binding

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.bindBoolToViewHidden
import dev.icerock.moko.mvvm.livedata.revert
import platform.UIKit.UIView

@Deprecated("use LiveData.bindToViewHidden extension")
fun UIView.bindVisibility(
    liveData: LiveData<Boolean>,
    inverted: Boolean = false
) {
    val source = if (inverted) liveData
    else liveData.revert()

    source.bindBoolToViewHidden(view = this)
}
