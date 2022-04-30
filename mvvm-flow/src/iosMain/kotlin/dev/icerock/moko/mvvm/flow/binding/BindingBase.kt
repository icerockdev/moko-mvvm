/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.flow.binding

import dev.icerock.moko.kswift.KSwiftExclude
import dev.icerock.moko.mvvm.flow.CStateFlow
import dev.icerock.moko.mvvm.flow.DisposableHandle
import kotlin.native.ref.WeakReference

@KSwiftExclude
fun <T, V : Any> V.bind(
    flow: CStateFlow<T>,
    setter: V.(T) -> Unit
): DisposableHandle {
    val weakView: WeakReference<V> = WeakReference(this)

    return flow.subscribe { value ->
        val strongView: V = weakView.get() ?: return@subscribe
        strongView.setter(value)
    }
}
