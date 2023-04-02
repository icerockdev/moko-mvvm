/*
 * Copyright 2023 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlin.reflect.KClass

@Composable
actual fun <T : ViewModel> getViewModel(
    key: String,
    klass: KClass<T>,
    viewModelBlock: () -> T
): T {
    val viewModel: T = remember(key, viewModelBlock)

    DisposableEffect(viewModel) {
        onDispose { viewModel.onCleared() }
    }

    return viewModel
}
