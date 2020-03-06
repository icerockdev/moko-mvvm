/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.utils

import dev.icerock.moko.mvvm.livedata.LiveData
import kotlin.native.ref.WeakReference

fun <T, V : Any> LiveData<T>.bind(view: V, setter: V.(T) -> Unit) {
    setter(view, value)
    val weakView = WeakReference(view)
    lateinit var observer: (T) -> Unit
    observer = { value ->
        val strongView = weakView.get()
        if (strongView == null) removeObserver(observer)
        else setter(strongView, value)
    }
    addObserver(observer)
}
