/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.utils.bind
import platform.UIKit.UIColor
import platform.UIKit.UIView
import platform.UIKit.backgroundColor
import platform.UIKit.hidden

fun UIView.bindBoolToViewBackgroundColor(
    liveData: LiveData<Boolean>,
    trueColor: UIColor,
    falseColor: UIColor
): Closeable {
    return bind(liveData) { value ->
        val color = when (value) {
            true -> trueColor
            false -> falseColor
        }

        backgroundColor = color
    }
}

fun UIView.bindBoolToViewHidden(
    liveData: LiveData<Boolean>
): Closeable {
    return bind(liveData) { value ->
        hidden = value
    }
}

fun UIView.bindBoolToViewFocus(
    liveData: LiveData<Boolean>
): Closeable {
    return bind(liveData) { value ->
        if (value) {
            becomeFirstResponder()
        } else {
            if (nextResponder?.canBecomeFirstResponder == true) {
                nextResponder?.becomeFirstResponder()
            } else {
                resignFirstResponder()
            }
        }
    }
}
