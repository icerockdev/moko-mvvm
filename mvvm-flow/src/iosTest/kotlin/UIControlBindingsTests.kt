/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import dev.icerock.moko.mvvm.flow.binding.bindEnabled
import dev.icerock.moko.mvvm.flow.binding.bindFocusTwoWay
import dev.icerock.moko.mvvm.flow.cMutableStateFlow
import dev.icerock.moko.mvvm.flow.cStateFlow
import kotlinx.cinterop.readValue
import kotlinx.coroutines.flow.MutableStateFlow
import platform.CoreGraphics.CGRectZero
import platform.Foundation.NSDate
import platform.Foundation.NSRunLoop
import platform.Foundation.date
import platform.Foundation.runUntilDate
import platform.UIKit.UITextField
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UIControlBindingsTests {

    private lateinit var destination: UITextField

    @BeforeTest
    fun setup() {
        destination = UITextField(frame = CGRectZero.readValue())
    }

    @Test
    fun `bool enabled`() {
        val source: MutableStateFlow<Boolean> = MutableStateFlow(false)
        destination.bindEnabled(source.cStateFlow())
        assertEquals(
            expected = false,
            actual = destination.enabled
        )
        source.value = true
        assertEquals(
            expected = true,
            actual = destination.enabled
        )
    }

    // disabled while not found way to sync becomeFirstResponder logic with current thread
//    @Test
    fun `bool two way focused`() {
        val source: MutableStateFlow<Boolean> = MutableStateFlow(false)
        destination.bindFocusTwoWay(source.cMutableStateFlow())
        assertEquals(
            expected = false,
            actual = destination.focused
        )

        source.value = true
        NSRunLoop.currentRunLoop.runUntilDate(NSDate.date())
        assertEquals(
            expected = true,
            actual = destination.focused
        )

        destination.resignFirstResponder()
        NSRunLoop.currentRunLoop.runUntilDate(NSDate.date())
        assertEquals(
            expected = false,
            actual = destination.focused
        )
        assertEquals(
            expected = false,
            actual = source.value
        )
    }
}
