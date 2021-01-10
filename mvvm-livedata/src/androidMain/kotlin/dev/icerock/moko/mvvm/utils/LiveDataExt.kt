/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import dev.icerock.moko.mvvm.livedata.LiveData

fun <T> LiveData<T>.bind(lifecycleOwner: LifecycleOwner, observer: (T?) -> Unit) {
    observer(value)

    this.ld().observe(lifecycleOwner, Observer { value ->
        observer(value)
    })
}

fun <T> LiveData<T>.bindNotNull(lifecycleOwner: LifecycleOwner, observer: (T) -> Unit) {
    bind(lifecycleOwner) { value ->
        if (value == null) return@bind

        observer(value)
    }
}
