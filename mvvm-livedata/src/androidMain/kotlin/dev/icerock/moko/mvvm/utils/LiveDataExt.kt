/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import dev.icerock.moko.mvvm.livedata.Closeable
import dev.icerock.moko.mvvm.livedata.LiveData

fun <T> LiveData<T>.bind(lifecycleOwner: LifecycleOwner, observer: (T?) -> Unit): Closeable {
    observer(value)

    val androidObserver = Observer<T> { value ->
        observer(value)
    }
    val androidLiveData = this.ld()

    androidLiveData.observe(lifecycleOwner, androidObserver)

    return Closeable {
        androidLiveData.removeObserver(androidObserver)
    }
}

fun <T> LiveData<T>.bindNotNull(lifecycleOwner: LifecycleOwner, observer: (T) -> Unit): Closeable {
    return bind(lifecycleOwner) { value ->
        if (value == null) return@bind

        observer(value)
    }
}
