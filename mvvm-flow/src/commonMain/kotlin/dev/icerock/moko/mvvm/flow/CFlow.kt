/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.flow

import kotlinx.coroutines.flow.Flow

expect class CFlow<T>(flow: Flow<T>) : Flow<T>

fun <T> Flow<T>.cFlow(): CFlow<T> = CFlow(this)
