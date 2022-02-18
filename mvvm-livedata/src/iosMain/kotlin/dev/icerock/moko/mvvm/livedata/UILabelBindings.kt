/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.utils.bind
import platform.UIKit.UILabel

fun <T : String?> UILabel.bindText(
    label: LiveData<T>
): Closeable {
    return bind(label) { this.text = it }
}
