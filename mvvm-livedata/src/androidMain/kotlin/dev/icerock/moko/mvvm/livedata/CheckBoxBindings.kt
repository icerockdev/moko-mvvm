/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import android.widget.CheckBox
import androidx.lifecycle.LifecycleOwner
import dev.icerock.moko.mvvm.utils.bindNotNull

fun LiveData<Boolean>.bindToCheckBoxChecked(lifecycleOwner: LifecycleOwner, checkBox: CheckBox) {
    bindNotNull(lifecycleOwner) { checkBox.isChecked = it }
}

fun MutableLiveData<Boolean>.bindTwoWayToCheckBoxChecked(
    lifecycleOwner: LifecycleOwner,
    checkBox: CheckBox
) {
    bindNotNull(lifecycleOwner) { value ->
        if (checkBox.isChecked == value) return@bindNotNull

        checkBox.isChecked = value
    }

    checkBox.setOnCheckedChangeListener { _, isChecked ->
        if (value == isChecked) return@setOnCheckedChangeListener

        value = isChecked
    }
}
