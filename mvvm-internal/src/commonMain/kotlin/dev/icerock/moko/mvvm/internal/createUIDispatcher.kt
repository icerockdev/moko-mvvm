/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.internal

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * We use coroutines native-mt version, so we can use Main dispatcher on both platforms
 */
fun createUIDispatcher(): CoroutineDispatcher = Dispatchers.Main
