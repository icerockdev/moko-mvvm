/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.binding

import dev.icerock.moko.mvvm.livedata.Closeable
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.bindBoolToSwitchOn
import dev.icerock.moko.mvvm.livedata.bindBoolTwoWayToSwitchOn
import platform.UIKit.UISwitch

@Deprecated("use LiveData.bindToSwitchOn extension")
fun UISwitch.bindValue(liveData: LiveData<Boolean>): Closeable {
    return liveData.bindBoolToSwitchOn(switch = this)
}

@Deprecated("use LiveData.bindTwoWayToSwitchOn extension")
fun UISwitch.bindValueTwoWay(liveData: MutableLiveData<Boolean>): Closeable {
    return liveData.bindBoolTwoWayToSwitchOn(switch = this)
}
