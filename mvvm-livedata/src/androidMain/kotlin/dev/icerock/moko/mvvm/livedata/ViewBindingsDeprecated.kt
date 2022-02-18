/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import android.view.View
import androidx.lifecycle.LifecycleOwner

@Deprecated(
    "Use View.bindToViewVisibleOrGone",
    replaceWith = ReplaceWith("View.bindToViewVisibleOrGone")
)
fun LiveData<Boolean>.bindToViewVisibleOrGone(
    lifecycleOwner: LifecycleOwner,
    view: View
): Closeable {
    return view.bindVisibleOrGone(lifecycleOwner, this)
}

@Deprecated(
    "Use View.bindToViewVisibleOrInvisible",
    replaceWith = ReplaceWith("View.bindToViewVisibleOrInvisible")
)
fun LiveData<Boolean>.bindToViewVisibleOrInvisible(
    lifecycleOwner: LifecycleOwner,
    view: View
): Closeable {
    return view.bindVisibleOrInvisible(lifecycleOwner, this)
}

@Deprecated(
    "Use View.bindEnabled",
    replaceWith = ReplaceWith("View.bindEnabled")
)
fun LiveData<Boolean>.bindToViewEnabled(
    lifecycleOwner: LifecycleOwner,
    view: View
): Closeable {
    return view.bindEnabled(lifecycleOwner, this)
}
