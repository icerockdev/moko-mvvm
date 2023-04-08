/*
 * Copyright 2023 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.compose

import androidx.compose.runtime.Composable
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlin.reflect.KClass

@Composable
expect fun <T : ViewModel> getViewModel(
    key: Any,
    klass: KClass<T>,
    viewModelBlock: () -> T
): T

// at now function can't be used on iOS because of https://youtrack.jetbrains.com/issue/KT-57727
//@Composable
//inline fun <reified T> getViewModel(
//    key: Any,
//    noinline viewModelBlock: @DisallowComposableCalls () -> T
//): T = remember(key, T::class, viewModelBlock)

/**
 * While https://youtrack.jetbrains.com/issue/KT-57727 it's best API that i can do.
 *
 * Created ViewModel will be saved during configuration change on Android and will call
 * `onCleared` when composition will destroyed.
 *
 * Usage:
 * ```
 * getViewModel(
 *   key = "some-key-of-current-navigation-screen",
 *   factory = viewModelFactory { SimpleViewModel() }
 * )
 * ```
 * @param key if key have same value with last recomposition - function return same ViewModel instance
 * @param factory ViewModel factory, used when key changed or ViewModel not created at all
 *
 * @return new created, or already exist ViewModel instance
 */
@Composable
fun <T : ViewModel> getViewModel(
    key: Any,
    factory: ViewModelFactory<T>
): T {
    return getViewModel(
        key = key,
        klass = factory.kClass,
        viewModelBlock = factory::createViewModel
    )
}
