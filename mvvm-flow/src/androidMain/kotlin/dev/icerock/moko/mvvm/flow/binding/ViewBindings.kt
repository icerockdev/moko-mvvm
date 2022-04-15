/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.flow.binding

import android.view.View
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.flow.StateFlow

fun View.bindVisibleOrGone(
    lifecycleOwner: LifecycleOwner,
    flow: StateFlow<Boolean>
): DisposableHandle {
    return flow.bind(lifecycleOwner) { value ->
        this.visibility = if (value) View.VISIBLE else View.GONE
    }
}

fun View.bindVisibleOrInvisible(
    lifecycleOwner: LifecycleOwner,
    flow: StateFlow<Boolean>
): DisposableHandle {
    return flow.bind(lifecycleOwner) { value ->
        this.visibility = if (value) View.VISIBLE else View.INVISIBLE
    }
}

fun View.bindEnabled(
    lifecycleOwner: LifecycleOwner,
    flow: StateFlow<Boolean>
): DisposableHandle {
    return flow.bind(lifecycleOwner) { this.isEnabled = it }
}
