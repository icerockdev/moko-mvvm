/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.bindFocus
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGRectZero
import platform.Foundation.NSDate
import platform.Foundation.NSRunLoop
import platform.Foundation.date
import platform.Foundation.runUntilDate
import platform.UIKit.UITextField
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

class UIResponderBindingsTests {

    private lateinit var destination: UITextField

    @BeforeTest
    fun setup() {
        destination = UITextField(frame = CGRectZero.readValue())
    }

    // disabled while not found way to sync becomeFirstResponder logic with current thread
//    @Test
    fun `bool focused`() {
        val source: MutableLiveData<Boolean> = MutableLiveData(false)
        destination.bindFocus(source)
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
    }
}
