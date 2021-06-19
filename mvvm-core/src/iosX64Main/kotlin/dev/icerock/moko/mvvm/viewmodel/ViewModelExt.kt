/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.viewmodel

import dev.icerock.moko.mvvm.core.cinterop.UIViewControllerLifecycleDelegateProtocol
import dev.icerock.moko.mvvm.core.cinterop.addLifecycleDelegate
import platform.UIKit.UIViewController
import platform.darwin.NSObject

internal actual fun ViewModel.setClearOnDetach(viewController: UIViewController) {
    viewController.addLifecycleDelegate(ViewModelLifecycle(this))
}

@Suppress("CONFLICTING_OVERLOADS")
private class ViewModelLifecycle(
    private val viewModel: ViewModel
) : NSObject(), UIViewControllerLifecycleDelegateProtocol {
    override fun viewController(
        viewController: UIViewController,
        willMoveToParentViewController: UIViewController?
    ) = Unit

    override fun viewController(
        viewController: UIViewController,
        didMoveToParentViewController: UIViewController?
    ) {
        if (didMoveToParentViewController == null) viewModel.onCleared()
    }
}
