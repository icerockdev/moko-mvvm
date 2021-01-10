/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.binding

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.bindToSwitchOn
import dev.icerock.moko.mvvm.livedata.bindTwoWayToSwitchOn
import platform.UIKit.UISwitch

@Deprecated("use LiveData.bindToSwitchOn extension")
fun UISwitch.bindValue(liveData: LiveData<Boolean>) {
    liveData.bindToSwitchOn(switch = this)
}

@Deprecated("use LiveData.bindTwoWayToSwitchOn extension")
fun UISwitch.bindValueTwoWay(liveData: MutableLiveData<Boolean>) {
    liveData.bindTwoWayToSwitchOn(switch = this)
}
