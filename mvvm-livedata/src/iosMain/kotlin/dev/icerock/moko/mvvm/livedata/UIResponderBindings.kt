/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import dev.icerock.moko.mvvm.utils.bind
import platform.UIKit.UIResponder

fun LiveData<Boolean>.bindToResponderFocus(responder: UIResponder) {
    bind(responder) { value ->
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
