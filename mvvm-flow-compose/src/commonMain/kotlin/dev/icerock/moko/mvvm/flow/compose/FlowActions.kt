/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.flow.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun <T> Flow<T>.observeAsActions(onEach: (T) -> Unit) {
    val flow = this
    LaunchedEffect(key1 = flow) {
        flow.onEach(onEach).collect()
    }
}
