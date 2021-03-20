/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.utils

import dev.icerock.moko.mvvm.livedata.Closeable
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.cinterop.UIViewLifecycleDelegateProtocol
import dev.icerock.moko.mvvm.livedata.cinterop.addLifecycleDelegate
import dev.icerock.moko.mvvm.livedata.cinterop.removeLifecycleDelegate
import platform.UIKit.UIView
import platform.UIKit.UIWindow
import platform.UIKit.window
import platform.darwin.NSObject
import kotlin.native.ref.WeakReference

actual fun <T, V : UIView> LiveData<T>.bind(view: V, setter: V.(T) -> Unit): Closeable {
    val observer = WeakObserver(view, setter)
    if (view.window != null) addObserver(observer)

    val lifecycleDelegate = ViewDelegate(
        liveData = this,
        observer = observer
    )
    val delegateId = view.addLifecycleDelegate(lifecycleDelegate)

    return Closeable {
        removeObserver(observer)
        view.removeLifecycleDelegate(delegateId)
    }
}

internal class WeakObserver<V : Any, T>(
    ref: V,
    private val setter: V.(T) -> Unit
) : (T) -> Unit {
    private val weakRef: WeakReference<V> = WeakReference(ref)

    override fun invoke(p1: T) {
        val strongRef = weakRef.value ?: return

        setter(strongRef, p1)
    }
}

internal class ViewDelegate<T>(
    liveData: LiveData<T>,
    private val observer: (T) -> Unit
) : NSObject(), UIViewLifecycleDelegateProtocol {
    private val weakLiveData: WeakReference<LiveData<T>> = WeakReference(liveData)

    private var movingToWindow: UIWindow? = null

    override fun view(view: UIView, willMoveToWindow: UIWindow?) {
        movingToWindow = willMoveToWindow
    }

    override fun viewDidMoveToWindow(view: UIView) {
        val liveData = weakLiveData.value ?: return

        if (movingToWindow == null) {
            liveData.removeObserver(observer)
        } else {
            liveData.addObserver(observer)
        }

        movingToWindow = null
    }
}
