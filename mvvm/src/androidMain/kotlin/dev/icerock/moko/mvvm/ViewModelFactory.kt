/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

class ViewModelFactory(private val viewModelBlock: () -> ViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return viewModelBlock() as T
    }
}

inline fun <reified T : ViewModel> ViewModelStoreOwner.getViewModel(noinline viewModelBlock: () -> T) =
    ViewModelProvider(this, ViewModelFactory {
        viewModelBlock()
    }).get(T::class.java)

inline fun <reified T : ViewModel> ViewModelStoreOwner.createViewModelFactory(noinline viewModelBlock: () -> T) =
    ViewModelFactory {
        viewModelBlock()
    }
