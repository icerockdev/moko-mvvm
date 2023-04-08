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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.icerock.moko.mvvm.getViewModel
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlin.reflect.KClass

@Composable
actual fun <T : ViewModel> getViewModel(
    key: Any,
    klass: KClass<T>,
    viewModelBlock: () -> T
): T {
    val context: Context = LocalContext.current
    val storeHolder: ViewModelStoreHolder = remember(context, key) {
        val viewModelStore: ViewModelStoreOwner = context as? ViewModelStoreOwner
            ?: error("context not implement ViewModelStoreOwner")

        val storeViewModel: StoreViewModel = viewModelStore.getViewModel { StoreViewModel() }
        storeViewModel.get(key)
    }
    val viewModel: T = remember(storeHolder) {
        ViewModelProvider(
            store = storeHolder.viewModelStore,
            factory = viewModelFactory { addInitializer(klass) { viewModelBlock() } }
        )[klass.java]
    }

    DisposableEffect(context, storeHolder) {
        onDispose {
            val componentActivity: ComponentActivity = context as? ComponentActivity
                ?: error("context should be ComponentActivity")

            if (!componentActivity.isChangingConfigurations) {
                storeHolder.viewModelStore.clear()
                storeHolder.disposeStore()
            }
        }
    }

    return viewModel
}

private class StoreViewModel : androidx.lifecycle.ViewModel() {
    private val stores: MutableMap<Any, ViewModelStore> = mutableMapOf()

    fun get(key: Any): ViewModelStoreHolder {
        val store: ViewModelStore = stores[key] ?: ViewModelStore()

        if (stores.containsKey(key).not()) {
            stores[key] = store
        }

        return ViewModelStoreHolder(
            viewModelStore = store,
            disposeStore = { stores.remove(key) }
        )
    }
}

private data class ViewModelStoreHolder(
    val viewModelStore: ViewModelStore,
    val disposeStore: () -> Unit
)
