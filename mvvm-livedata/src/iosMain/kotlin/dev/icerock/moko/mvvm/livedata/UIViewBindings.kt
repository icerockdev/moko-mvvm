/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.utils.bind
import platform.UIKit.UIColor
import platform.UIKit.UIView
import platform.UIKit.backgroundColor
import platform.UIKit.hidden

fun LiveData<Boolean>.bindBoolToViewBackgroundColor(
    view: UIView,
    trueColor: UIColor,
    falseColor: UIColor
) {
    bind(view) { value ->
        val color = when (value) {
            true -> trueColor
            false -> falseColor
        }

        backgroundColor = color
    }
}

fun LiveData<Boolean>.bindBoolToViewHidden(
    view: UIView
) {
    bind(view) { value ->
        hidden = value
    }
}

fun LiveData<Boolean>.bindBoolToViewFocus(view: UIView) {
    bind(view) { value ->
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
