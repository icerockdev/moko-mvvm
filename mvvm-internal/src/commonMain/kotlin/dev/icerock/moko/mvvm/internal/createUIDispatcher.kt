/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.internal

import kotlinx.coroutines.CoroutineDispatcher

expect fun createUIDispatcher(): CoroutineDispatcher
