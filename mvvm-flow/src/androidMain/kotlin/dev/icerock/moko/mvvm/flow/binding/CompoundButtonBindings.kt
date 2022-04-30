/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.flow.binding

import android.widget.CompoundButton
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun CompoundButton.bindChecked(
    lifecycleOwner: LifecycleOwner,
    flow: StateFlow<Boolean>
): DisposableHandle {
    return flow.bind(lifecycleOwner) { this.isChecked = it }
}

fun CompoundButton.bindCheckedTwoWay(
    lifecycleOwner: LifecycleOwner,
    flow: MutableStateFlow<Boolean>
): DisposableHandle {
    val readDisposable: DisposableHandle = flow.bind(lifecycleOwner) { value ->
        if (this.isChecked == value) return@bind

        this.isChecked = value
    }

    val checkListener = CompoundButton.OnCheckedChangeListener { _, isChecked ->
        if (flow.value == isChecked) return@OnCheckedChangeListener

        flow.value = isChecked
    }
    setOnCheckedChangeListener(checkListener)

    return DisposableHandle {
        readDisposable.dispose()
        setOnCheckedChangeListener(null)
    }
}
