/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import dev.icerock.moko.mvvm.livedata.LiveData

@Composable
actual fun <T> LiveData<T>.observeAsState(): State<T> {
    val self: LiveData<T> = this
    val state = remember { mutableStateOf(self.value) }
    DisposableEffect(self) {
        val observer: (T) -> Unit = { state.value = it }
        self.addObserver(observer)
        onDispose { removeObserver(observer) }
    }
    return state
}
