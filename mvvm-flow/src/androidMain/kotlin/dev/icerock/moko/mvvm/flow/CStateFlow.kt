/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.flow

import kotlinx.coroutines.flow.StateFlow

actual open class CStateFlow<T> actual constructor(
    private val flow: StateFlow<T>
) : StateFlow<T> by flow
