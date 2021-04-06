/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.test

import dev.icerock.moko.mvvm.internal.createViewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object TestViewModelScope {
    private val originalScope = createViewModelScope

    fun setupViewModelScope(coroutineScope: CoroutineScope) {
        createViewModelScope = { coroutineScope }
    }

    fun resetViewModelScope() {
        createViewModelScope = originalScope
    }
}
