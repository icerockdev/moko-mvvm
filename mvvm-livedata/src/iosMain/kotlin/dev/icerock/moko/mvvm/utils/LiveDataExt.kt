/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.utils

import dev.icerock.moko.mvvm.livedata.LiveData
import platform.UIKit.UIView

expect fun <T, V : UIView> LiveData<T>.bind(view: V, setter: V.(T) -> Unit)
