/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import dev.icerock.moko.mvvm.livedata.LiveData

@Composable
expect fun <T> LiveData<T>.observeAsState(): State<T>
