/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import android.view.View
import androidx.lifecycle.LifecycleOwner
import dev.icerock.moko.mvvm.utils.bindNotNull

fun View.bindVisibleOrGone(
    lifecycleOwner: LifecycleOwner,
    liveData: LiveData<Boolean>
): Closeable {
    return liveData.bindNotNull(lifecycleOwner) { value ->
        this.visibility = if (value) View.VISIBLE else View.GONE
    }
}

fun View.bindVisibleOrInvisible(
    lifecycleOwner: LifecycleOwner,
    liveData: LiveData<Boolean>
): Closeable {
    return liveData.bindNotNull(lifecycleOwner) { value ->
        this.visibility = if (value) View.VISIBLE else View.INVISIBLE
    }
}

fun View.bindEnabled(
    lifecycleOwner: LifecycleOwner,
    liveData: LiveData<Boolean>
): Closeable {
    return liveData.bindNotNull(lifecycleOwner) { this.isEnabled = it }
}
