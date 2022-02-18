/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.lifecycle.LifecycleOwner
import dev.icerock.moko.mvvm.utils.bindNotNull

@Deprecated(
    "Use CompoundButton.bindChecked",
    replaceWith = ReplaceWith("CompoundButton.bindChecked")
)
fun LiveData<Boolean>.bindToCheckBoxChecked(
    lifecycleOwner: LifecycleOwner,
    checkBox: CheckBox
): Closeable {
    return checkBox.bindChecked(lifecycleOwner, this)
}

@Deprecated(
    "Use CompoundButton.bindCheckedTwoWay",
    replaceWith = ReplaceWith("CompoundButton.bindCheckedTwoWay")
)
fun MutableLiveData<Boolean>.bindTwoWayToCheckBoxChecked(
    lifecycleOwner: LifecycleOwner,
    checkBox: CheckBox
): Closeable {
    val readCloseable = bindNotNull(lifecycleOwner) { value ->
        if (checkBox.isChecked == value) return@bindNotNull

        checkBox.isChecked = value
    }

    val checkListener = CompoundButton.OnCheckedChangeListener { _, isChecked ->
        if (value == isChecked) return@OnCheckedChangeListener

        value = isChecked
    }
    checkBox.setOnCheckedChangeListener(checkListener)

    val writeCloseable = Closeable {
        checkBox.setOnCheckedChangeListener(null)
    }

    return readCloseable + writeCloseable
}
