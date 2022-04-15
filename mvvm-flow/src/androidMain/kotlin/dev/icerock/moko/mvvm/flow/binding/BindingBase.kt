/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.flow.binding

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

fun <T> StateFlow<T>.bind(
    lifecycleOwner: LifecycleOwner,
    observer: (T) -> Unit
): DisposableHandle {
    val self: StateFlow<T> = this
    val job: Job = lifecycleOwner.lifecycleScope.launchWhenStarted {
        self.onEach { observer(it) }.collect()
    }
    return DisposableHandle { job.cancel() }
}
