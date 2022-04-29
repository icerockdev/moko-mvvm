/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.flow.binding

import dev.icerock.moko.mvvm.flow.CStateFlow
import dev.icerock.moko.mvvm.flow.DisposableHandle
import platform.UIKit.UIButton
import platform.UIKit.UIControlStateNormal
import platform.UIKit.UIImage

fun <T : String?> UIButton.bindTitle(flow: CStateFlow<T>): DisposableHandle {
    return bind(flow) { value ->
        setTitle(value, forState = UIControlStateNormal)
    }
}

fun UIButton.bindImage(
    flow: CStateFlow<Boolean>,
    trueImage: UIImage,
    falseImage: UIImage
): DisposableHandle {
    return bind(flow) { value ->
        val image = when (value) {
            true -> trueImage
            false -> falseImage
        }

        setImage(image, forState = UIControlStateNormal)
    }
}
