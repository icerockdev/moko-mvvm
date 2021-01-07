/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule

actual open class BaseTestsClass {
    @get:Rule
    val rule = InstantTaskExecutorRule()
}
