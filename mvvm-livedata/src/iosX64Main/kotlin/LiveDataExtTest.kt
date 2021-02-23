/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.utils

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.cinterop.UIViewLifecycleDelegateProtocol
import dev.icerock.moko.mvvm.livedata.cinterop.addLifecycleDelegate
import platform.UIKit.UIView
import platform.UIKit.UIWindow
import platform.UIKit.window
import platform.darwin.NSObject
import kotlin.native.ref.WeakReference

actual fun <T, V : UIView> LiveData<T>.bind(view: V, setter: V.(T) -> Unit) {
    val observer = WeakObserver(view, setter)
    if (view.window != null) addObserver(observer)

    val lifecycleDelegate = ViewDelegate(
        liveData = this,
        observer = observer
    )
    view.addLifecycleDelegate(lifecycleDelegate)
}

private class WeakObserver<V : Any, T>(
    ref: V,
    private val setter: V.(T) -> Unit
) : (T) -> Unit {
    private val weakRef: WeakReference<V> = WeakReference(ref)

    override fun invoke(p1: T) {
        val strongRef = weakRef.get() ?: return

        setter(strongRef, p1)
    }
}

private class ViewDelegate<T>(
    private val liveData: LiveData<T>,
    private val observer: (T) -> Unit
) : NSObject(), UIViewLifecycleDelegateProtocol {
    private var movingToWindow: UIWindow? = null

    override fun view(view: UIView, willMoveToWindow: UIWindow?) {
        movingToWindow = willMoveToWindow
    }

    override fun viewDidMoveToWindow(view: UIView) {
        if (movingToWindow == null) {
            liveData.removeObserver(observer)
        } else {
            liveData.addObserver(observer)
        }
    }
}
