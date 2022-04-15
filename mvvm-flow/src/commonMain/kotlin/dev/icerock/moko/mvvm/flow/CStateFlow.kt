/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.flow

import kotlinx.coroutines.flow.StateFlow

expect open class CStateFlow<T>(flow: StateFlow<T>) : StateFlow<T>

fun <T> StateFlow<T>.cStateFlow(): CStateFlow<T> = CStateFlow(this)
