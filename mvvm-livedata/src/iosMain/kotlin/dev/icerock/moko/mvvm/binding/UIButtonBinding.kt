/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.binding

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.bindToButtonImage
import dev.icerock.moko.mvvm.livedata.bindToButtonTitle
import dev.icerock.moko.mvvm.livedata.bindToControlEnabled
import dev.icerock.moko.mvvm.livedata.bindToViewBackgroundColor
import dev.icerock.moko.resources.desc.StringDesc
import platform.UIKit.UIButton
import platform.UIKit.UIColor
import platform.UIKit.UIImage

@Deprecated("use LiveData.bindToControlEnabled & LiveData.bindToViewBackgroundColor extension")
fun UIButton.bindEnabled(
    liveData: LiveData<Boolean>,
    enabledColor: UIColor? = null,
    disabledColor: UIColor? = null
) {
    liveData.bindToControlEnabled(control = this)

    if (enabledColor != null && disabledColor != null) {
        liveData.bindToViewBackgroundColor(
            view = this,
            trueColor = enabledColor,
            falseColor = disabledColor
        )
    }
}

@Deprecated("use LiveData.bindToButtonTitle extension")
fun UIButton.bindTitle(
    liveData: LiveData<String>
) {
    liveData.bindToButtonTitle(button = this)
}

@Deprecated("use LiveData.bindToButtonTitle extension")
fun UIButton.bindTitle(
    liveData: LiveData<StringDesc>
) {
    liveData.bindToButtonTitle(button = this)
}

@Deprecated("use LiveData.bindToButtonImage extension")
fun UIButton.bindImages(
    liveData: LiveData<Boolean>,
    trueImage: UIImage,
    falseImage: UIImage
) {
    liveData.bindToButtonImage(
        button = this,
        trueImage = trueImage,
        falseImage = falseImage
    )
}
