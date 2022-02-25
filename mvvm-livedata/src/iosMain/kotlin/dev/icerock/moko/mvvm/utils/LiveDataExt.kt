/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.utils

import dev.icerock.moko.kswift.KSwiftExclude
import dev.icerock.moko.mvvm.livedata.Closeable
import dev.icerock.moko.mvvm.livedata.LiveData
import kotlin.native.ref.WeakReference

@KSwiftExclude
fun <T, V : Any> V.bind(liveData: LiveData<T>, setter: V.(T) -> Unit): Closeable {
    setter(this, liveData.value)
    val weakView: WeakReference<V> = WeakReference(this)

    @Suppress("JoinDeclarationAndAssignment")
    lateinit var observer: (T) -> Unit
    observer = { value ->
        val strongView = weakView.get()
        if (strongView == null) liveData.removeObserver(observer)
        else setter(strongView, value)
    }
    liveData.addObserver(observer)

    return Closeable {
        liveData.removeObserver(observer)
    }
}
