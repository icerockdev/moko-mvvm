/*
 * Copyright 2023 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.compose

import androidx.compose.runtime.Composable
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlin.reflect.KClass

@Composable
expect fun <T : ViewModel> getViewModel(
    key: String,
    klass: KClass<T>,
    viewModelBlock: () -> T
): T

@Composable
inline fun <reified T : ViewModel> getViewModel(
    key: String,
    noinline viewModelBlock: () -> T
): T = getViewModel(key, T::class, viewModelBlock)
