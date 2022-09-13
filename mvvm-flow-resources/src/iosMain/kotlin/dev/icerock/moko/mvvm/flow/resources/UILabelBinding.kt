/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.flow.resources

import dev.icerock.moko.mvvm.flow.CStateFlow
import dev.icerock.moko.mvvm.flow.DisposableHandle
import dev.icerock.moko.mvvm.flow.binding.bind
import dev.icerock.moko.resources.desc.StringDesc
import platform.UIKit.UILabel

fun <T : StringDesc?> UILabel.bindText(
    flow: CStateFlow<T>
): DisposableHandle {
    return bind(flow) { this.text = it?.localized() }
}
