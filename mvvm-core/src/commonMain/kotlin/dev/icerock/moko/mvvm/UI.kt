/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm

import dev.icerock.moko.mvvm.internal.createUIDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

val Dispatchers.UI: CoroutineDispatcher
    get() = createUIDispatcher()
