/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras

class ViewModelFactory(
    private val viewModelBlock: () -> ViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        @Suppress("UNCHECKED_CAST")
        return viewModelBlock() as T
    }
}

inline fun <reified T : ViewModel> ViewModelStoreOwner.getViewModel(
    noinline viewModelBlock: () -> T
): T = ViewModelProvider(
    owner = this,
    factory = ViewModelFactory(viewModelBlock)
)[T::class.java]

inline fun <reified T : ViewModel> createViewModelFactory(
    noinline viewModelBlock: () -> T
): ViewModelFactory = ViewModelFactory(viewModelBlock)
