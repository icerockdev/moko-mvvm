/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

expect class CFlow<T>(flow: Flow<T>) : Flow<T>

expect class CStateFlow<T>(flow: StateFlow<T>) : StateFlow<T>

fun <T> Flow<T>.cFlow(): CFlow<T> = CFlow(this)
fun <T> StateFlow<T>.cStateFlow(): CStateFlow<T> = CStateFlow(this)
