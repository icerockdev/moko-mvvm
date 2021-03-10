/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.binding

import dev.icerock.moko.mvvm.livedata.Closeable
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.bindBoolToButtonImage
import dev.icerock.moko.mvvm.livedata.bindBoolToControlEnabled
import dev.icerock.moko.mvvm.livedata.bindBoolToViewBackgroundColor
import dev.icerock.moko.mvvm.livedata.bindStringDescToButtonTitle
import dev.icerock.moko.mvvm.livedata.bindStringToButtonTitle
import dev.icerock.moko.resources.desc.StringDesc
import platform.UIKit.UIButton
import platform.UIKit.UIColor
import platform.UIKit.UIImage

@Deprecated("use LiveData.bindToControlEnabled & LiveData.bindToViewBackgroundColor extension")
fun UIButton.bindEnabled(
    liveData: LiveData<Boolean>,
    enabledColor: UIColor? = null,
    disabledColor: UIColor? = null
): Closeable {
    val enabledCloseable = liveData.bindBoolToControlEnabled(control = this)

    if (enabledColor != null && disabledColor != null) {
        val backgroundCloseable = liveData.bindBoolToViewBackgroundColor(
            view = this,
            trueColor = enabledColor,
            falseColor = disabledColor
        )
        return enabledCloseable + backgroundCloseable
    } else {
        return enabledCloseable
    }
}

@Deprecated("use LiveData.bindToButtonTitle extension")
fun UIButton.bindTitle(
    liveData: LiveData<String>
): Closeable {
    return liveData.bindStringToButtonTitle(button = this)
}

@Deprecated("use LiveData.bindToButtonTitle extension")
fun UIButton.bindTitle(
    liveData: LiveData<StringDesc>
): Closeable {
    return liveData.bindStringDescToButtonTitle(button = this)
}

@Deprecated("use LiveData.bindToButtonImage extension")
fun UIButton.bindImages(
    liveData: LiveData<Boolean>,
    trueImage: UIImage,
    falseImage: UIImage
): Closeable {
    return liveData.bindBoolToButtonImage(
        button = this,
        trueImage = trueImage,
        falseImage = falseImage
    )
}
