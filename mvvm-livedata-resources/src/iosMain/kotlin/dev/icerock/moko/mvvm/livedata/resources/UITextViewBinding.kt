/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata.resources

import dev.icerock.moko.mvvm.livedata.Closeable
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.bindStringToTextViewText
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.resources.desc.StringDesc
import platform.UIKit.UITextView

fun <T : StringDesc?> UITextView.bindText(
    liveData: LiveData<T>
): Closeable {
    return bindStringToTextViewText(liveData.map { it?.localized() })
}
