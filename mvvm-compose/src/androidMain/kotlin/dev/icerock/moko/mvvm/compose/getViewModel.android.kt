/*
 * Copyright 2023 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.compose

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ComponentActivity
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import dev.icerock.moko.mvvm.getViewModel
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlin.reflect.KClass

@Composable
actual fun <T : ViewModel> getViewModel(
    key: String,
    klass: KClass<T>,
    viewModelBlock: () -> T
): T {
    val context: Context = LocalContext.current
    val viewModel: T = remember(key, context) {
        val viewModelStore: ViewModelStoreOwner = context as? ViewModelStoreOwner
            ?: throw IllegalStateException("context not implement ViewModelStoreOwner")

        viewModelStore.getViewModel(
            key = key,
            klass = klass,
            viewModelBlock = viewModelBlock
        )
    }

    DisposableEffect(viewModel, context) {
        onDispose {
            val componentActivity: ComponentActivity = context as? ComponentActivity
                ?: throw IllegalStateException("context should be ComponentActivity")
            val viewModelStore: ViewModelStoreOwner = context as? ViewModelStoreOwner
                ?: throw IllegalStateException("context not implement ViewModelStoreOwner")

            if (!componentActivity.isChangingConfigurations) {
                viewModel.onCleared()
            }
        }
    }

    return viewModel
}

private class StoreViewModel : androidx.lifecycle.ViewModel(), ViewModelStoreOwner {
    override val viewModelStore: ViewModelStore = ViewModelStore()
}
