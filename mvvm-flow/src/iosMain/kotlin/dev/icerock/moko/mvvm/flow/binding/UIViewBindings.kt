/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.flow.binding

import dev.icerock.moko.mvvm.flow.CStateFlow
import dev.icerock.moko.mvvm.flow.DisposableHandle
import platform.UIKit.UIColor
import platform.UIKit.UIView
import platform.UIKit.backgroundColor
import platform.UIKit.hidden

fun UIView.bindBackgroundColor(
    flow: CStateFlow<Boolean>,
    trueColor: UIColor,
    falseColor: UIColor
): DisposableHandle {
    return bind(flow) { value ->
        val color = when (value) {
            true -> trueColor
            false -> falseColor
        }

        backgroundColor = color
    }
}

fun UIView.bindHidden(
    flow: CStateFlow<Boolean>
): DisposableHandle {
    return bind(flow) { value ->
        hidden = value
    }
}

fun UIView.bindFocus(
    flow: CStateFlow<Boolean>
): DisposableHandle {
    return bind(flow) { value ->
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
