/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.utils.bind
import platform.UIKit.UIButton
import platform.UIKit.UIControlStateNormal
import platform.UIKit.UIImage

fun <T : String?> UIButton.bindTitle(liveData: LiveData<T>): Closeable {
    return bind(liveData) { value ->
        setTitle(value, forState = UIControlStateNormal)
    }
}

fun UIButton.bindImage(
    liveData: LiveData<Boolean>,
    trueImage: UIImage,
    falseImage: UIImage
): Closeable {
    return bind(liveData) { value ->
        val image = when (value) {
            true -> trueImage
            false -> falseImage
        }

        setImage(image, forState = UIControlStateNormal)
    }
}
