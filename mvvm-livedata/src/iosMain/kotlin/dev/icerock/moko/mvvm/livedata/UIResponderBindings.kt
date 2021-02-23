/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import platform.UIKit.UIView

@Deprecated(
    message = "we can't use only UIResponder because need UIView lifecycle",
    replaceWith = ReplaceWith(
        expression = "bindBoolToViewFocus",
        "dev.icerock.moko.mvvm.livedata.bindBoolToViewFocus"
    ),
    level = DeprecationLevel.WARNING
)
fun LiveData<Boolean>.bindBoolToResponderFocus(responder: UIView) = bindBoolToViewFocus(responder)
