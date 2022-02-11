/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata.resources

import dev.icerock.moko.mvvm.livedata.Closeable
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.bindStringToTextFieldText
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.resources.desc.StringDesc
import platform.UIKit.UITextField

fun <T : StringDesc?> UITextField.bindText(
    liveData: LiveData<T>
): Closeable {
    return bindStringToTextFieldText(liveData.map { it?.localized() })
}
