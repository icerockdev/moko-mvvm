/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.bindSwitchOn
import dev.icerock.moko.mvvm.livedata.bindSwitchOnTwoWay
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGRectZero
import platform.Foundation.NSDate
import platform.Foundation.NSRunLoop
import platform.Foundation.date
import platform.Foundation.runUntilDate
import platform.UIKit.UISwitch
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UISwitchBindingsTests {

    private lateinit var destination: UISwitch

    @BeforeTest
    fun setup() {
        destination = UISwitch(frame = CGRectZero.readValue())
    }

    @Test
    fun `bool switch on`() {
        val source: MutableLiveData<Boolean> = MutableLiveData(false)
        destination.bindSwitchOn(source)
        assertEquals(
            expected = false,
            actual = destination.isOn()
        )
        source.value = true
        assertEquals(
            expected = true,
            actual = destination.isOn()
        )
    }

    // disabled while not found way to sync setOn logic with current thread
//    @Test
    fun `bool two way switch on`() {
        val source: MutableLiveData<Boolean> = MutableLiveData(false)
        destination.bindSwitchOnTwoWay(source)
        assertEquals(
            expected = false,
            actual = destination.isOn()
        )

        source.value = true
        assertEquals(
            expected = true,
            actual = destination.isOn()
        )

        destination.setOn(false, animated = false)
        NSRunLoop.currentRunLoop.runUntilDate(NSDate.date())
        assertEquals(
            expected = false,
            actual = source.value
        )
    }
}
