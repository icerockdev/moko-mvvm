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

// with this inline function we got error
// Module "dev.icerock.moko:mvvm-compose (dev.icerock.moko:mvvm-compose-iossimulatorarm64)" has a
// reference to symbol [ dev.icerock.moko.mvvm.compose/getViewModel|-1374970681312300217[0]
// <- local Local[<TP>,0 | TYPE_PARAMETER name:T index:0 variance:
// superTypes:[dev.icerock.moko.mvvm.viewmodel.ViewModel] reified:true] ].
// Neither the module itself nor its dependencies contain such declaration.
@Composable
inline fun <reified T : ViewModel> getViewModel(
    key: Any,
    noinline viewModelBlock: () -> T
): T = getViewModel(key, T::class, viewModelBlock)
