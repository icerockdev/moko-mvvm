/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import android.view.View
import androidx.lifecycle.LifecycleOwner
import dev.icerock.moko.mvvm.utils.bindNotNull

fun LiveData<Boolean>.bindToViewVisibleOrGone(lifecycleOwner: LifecycleOwner, view: View) {
    bindNotNull(lifecycleOwner) { value ->
        if (value) view.visibility = View.VISIBLE
        else view.visibility = View.GONE
    }
}

fun LiveData<Boolean>.bindToViewVisibleOrInvisible(lifecycleOwner: LifecycleOwner, view: View) {
    bindNotNull(lifecycleOwner) { value ->
        if (value) view.visibility = View.VISIBLE
        else view.visibility = View.INVISIBLE
    }
}

fun LiveData<Boolean>.bindToViewEnabled(lifecycleOwner: LifecycleOwner, view: View) {
    bindNotNull(lifecycleOwner) { view.isEnabled = it }
}
