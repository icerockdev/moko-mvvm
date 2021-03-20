/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.viewmodel

import platform.UIKit.UIViewController

internal expect fun ViewModel.setClearOnDetach(viewController: UIViewController)
