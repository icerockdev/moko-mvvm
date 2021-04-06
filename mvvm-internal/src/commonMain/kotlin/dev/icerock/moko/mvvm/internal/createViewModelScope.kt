/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.internal

import kotlinx.coroutines.CoroutineScope
import kotlin.native.concurrent.ThreadLocal

/**
 * Factory of viewModelScope. Internal API, for ability of mvvm-test to change viewModelScope
 * dispatcher.
 *
 * In default implementation create main-thread dispatcher scope.
 */
@ThreadLocal
var createViewModelScope: () -> CoroutineScope = {
    CoroutineScope(createUIDispatcher())
}
