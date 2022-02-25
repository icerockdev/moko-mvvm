/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata.swiperefresh

import androidx.lifecycle.LifecycleOwner
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dev.icerock.moko.mvvm.livedata.Closeable
import dev.icerock.moko.mvvm.livedata.MutableLiveData

@Deprecated(
    "Use SwipeRefreshLayout.bindRefreshingTwoWay",
    replaceWith = ReplaceWith("SwipeRefreshLayout.bindRefreshingTwoWay")
)
fun MutableLiveData<Boolean>.bindToSwipeRefreshLayoutRefreshing(
    lifecycleOwner: LifecycleOwner,
    swipeRefreshLayout: SwipeRefreshLayout
): Closeable {
    return swipeRefreshLayout.bindRefreshingTwoWay(lifecycleOwner, this)
}
