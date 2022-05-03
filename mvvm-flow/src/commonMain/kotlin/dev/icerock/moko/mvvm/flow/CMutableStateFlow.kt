/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.flow

import kotlinx.coroutines.flow.MutableStateFlow

expect class CMutableStateFlow<T>(flow: MutableStateFlow<T>) : MutableStateFlow<T>

fun <T> MutableStateFlow<T>.cMutableStateFlow(): CMutableStateFlow<T> = CMutableStateFlow(this)

fun <T> MutableStateFlow<T>.test() {}
