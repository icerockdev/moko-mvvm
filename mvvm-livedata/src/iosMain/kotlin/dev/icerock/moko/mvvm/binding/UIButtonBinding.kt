/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.binding

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.resources.desc.StringDesc
import platform.UIKit.UIButton
import platform.UIKit.UIColor
import platform.UIKit.UIControlStateNormal
import platform.UIKit.UIImage
import platform.UIKit.backgroundColor
import dev.icerock.moko.mvvm.utils.bind

fun UIButton.bindEnabled(
    liveData: LiveData<Boolean>,
    enabledColor: UIColor? = null,
    disabledColor: UIColor? = null
) {
    liveData.bind(this) { value ->
        this.enabled = value

        val color = when (value) {
            true -> enabledColor
            false -> disabledColor
        }

        color?.also { backgroundColor = it }
    }
}

fun UIButton.bindTitle(
    liveData: LiveData<String>
) {
    liveData.bind(this) { value ->
        setTitle(value, forState = UIControlStateNormal)
    }
}

fun UIButton.bindTitle(
    liveData: LiveData<StringDesc>
) {
    bindTitle(liveData.map { it.localized() })
}

fun UIButton.bindImages(
    liveData: LiveData<Boolean>,
    trueImage: UIImage,
    falseImage: UIImage
) {
    liveData.bind(this) { value ->
        val image = when (value) {
            true -> trueImage
            false -> falseImage
        }
        setImage(image, forState = UIControlStateNormal)
    }
}
