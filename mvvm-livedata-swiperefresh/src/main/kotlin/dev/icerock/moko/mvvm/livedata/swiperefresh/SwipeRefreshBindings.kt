/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata.swiperefresh

import androidx.lifecycle.LifecycleOwner
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dev.icerock.moko.mvvm.livedata.Closeable
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.utils.bindNotNull
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun MutableLiveData<Boolean>.bindToSwipeRefreshLayoutRefreshing(
    lifecycleOwner: LifecycleOwner,
    swipeRefreshLayout: SwipeRefreshLayout
): Closeable {
    val readCloseable = bindNotNull(lifecycleOwner) { swipeRefreshLayout.isRefreshing = it }

    swipeRefreshLayout.setOnRefreshListener { value = true }

    val writeCloseable = Closeable {
        swipeRefreshLayout.setOnRefreshListener(null)
    }

    return readCloseable + writeCloseable
}

fun SwipeRefreshLayout.setRefreshAction(
    coroutineScope: CoroutineScope,
    block: suspend () -> Unit
) {
    setOnRefreshListener {
        coroutineScope.launch {
            block()
            isRefreshing = false
        }
    }
}

fun SwipeRefreshLayout.setRefreshAction(
    block: (completion: () -> Unit) -> Unit
) {
    setOnRefreshListener {
        block {
            isRefreshing = false
        }
    }
}
