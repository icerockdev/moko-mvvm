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

fun SwipeRefreshLayout.bindRefreshingTwoWay(
    lifecycleOwner: LifecycleOwner,
    liveData: MutableLiveData<Boolean>
): Closeable {
    val readCloseable = liveData.bindNotNull(lifecycleOwner) { value ->
        if (this.isRefreshing == value) return@bindNotNull
        this.isRefreshing = value
    }

    this.setOnRefreshListener { liveData.value = true }

    val writeCloseable = Closeable {
        this.setOnRefreshListener(null)
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
