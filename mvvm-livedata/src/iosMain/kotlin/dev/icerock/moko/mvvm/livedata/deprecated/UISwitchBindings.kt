/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch")

package dev.icerock.moko.mvvm.livedata

import platform.UIKit.UISwitch

@Deprecated(
    "Use UISwitch.bindSwitchOn",
    replaceWith = ReplaceWith("UISwitch.bindSwitchOn")
)
fun LiveData<Boolean>.bindBoolToSwitchOn(
    switch: UISwitch
): Closeable {
    return switch.bindSwitchOn(this)
}

@Deprecated(
    "Use UISwitch.bindSwitchOnTwoWay",
    replaceWith = ReplaceWith("UISwitch.bindSwitchOnTwoWay")
)
fun MutableLiveData<Boolean>.bindBoolTwoWayToSwitchOn(
    switch: UISwitch
): Closeable {
    return switch.bindSwitchOnTwoWay(this)
}
