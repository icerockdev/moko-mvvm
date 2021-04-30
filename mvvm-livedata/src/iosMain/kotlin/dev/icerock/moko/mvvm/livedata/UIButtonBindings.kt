/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.utils.bind
import dev.icerock.moko.resources.desc.StringDesc
import platform.UIKit.UIButton
import platform.UIKit.UIControlStateNormal
import platform.UIKit.UIImage

fun <T : String?> LiveData<T>.bindStringToButtonTitle(button: UIButton): Closeable {
    return bind(button) { value ->
        setTitle(value, forState = UIControlStateNormal)
    }
}

fun <T : StringDesc?> LiveData<T>.bindStringDescToButtonTitle(button: UIButton): Closeable {
    return map { it?.localized() }.bindStringToButtonTitle(button)
}

fun LiveData<Boolean>.bindBoolToButtonImage(
    button: UIButton,
    trueImage: UIImage,
    falseImage: UIImage
): Closeable {
    return bind(button) { value ->
        val image = when (value) {
            true -> trueImage
            false -> falseImage
        }

        setImage(image, forState = UIControlStateNormal)
    }
}
