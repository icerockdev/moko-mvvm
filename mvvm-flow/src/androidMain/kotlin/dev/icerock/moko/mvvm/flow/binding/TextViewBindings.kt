/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.flow.binding

import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.flow.StateFlow

fun TextView.bindText(
    lifecycleOwner: LifecycleOwner,
    flow: StateFlow<String>
): DisposableHandle {
    return flow.bind(lifecycleOwner) { this.text = it }
}
