/*
 * Copyright 2023 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.compose

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlin.reflect.KClass

interface ViewModelFactory<T : ViewModel> {
    val kClass: KClass<T>

    fun createViewModel(): T
}

inline fun <reified T : ViewModel> viewModelFactory(
    crossinline builder: () -> T
): ViewModelFactory<T> {
    return object : ViewModelFactory<T> {
        override val kClass: KClass<T> = T::class
        override fun createViewModel(): T = builder()
    }
}
