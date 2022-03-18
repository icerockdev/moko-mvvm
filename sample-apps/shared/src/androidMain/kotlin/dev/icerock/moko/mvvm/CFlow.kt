/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

actual class CFlow<T> actual constructor(private val flow: Flow<T>) : Flow<T> by flow
actual class CStateFlow<T> actual constructor(private val flow: StateFlow<T>) : StateFlow<T> by flow
