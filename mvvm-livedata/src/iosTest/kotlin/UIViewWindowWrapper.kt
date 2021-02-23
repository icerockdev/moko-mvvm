/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIView
import platform.UIKit.UIViewController
import platform.UIKit.UIWindow
import platform.UIKit.addSubview

fun UIView.wrapIntoWindow(): UIWindow {
    val screenFrame = CGRectMake(x = 0.0, y = 0.0, width = 720.0, height = 1366.0)
    val window = UIWindow(frame = screenFrame)

    val viewController = UIViewController()
    viewController.view.addSubview(this)

    window.rootViewController = viewController

    return window
}
