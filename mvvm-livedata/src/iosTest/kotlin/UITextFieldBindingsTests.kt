/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.bindStringToTextFieldText
import dev.icerock.moko.mvvm.livedata.bindStringTwoWayToTextFieldText
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGRectZero
import platform.UIKit.UITextField
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UITextFieldBindingsTests {

    private lateinit var destination: UITextField

    @BeforeTest
    fun setup() {
        destination = UITextField(frame = CGRectZero.readValue())
    }

    @Test
    fun `nonnullable string text`() {
        val source: MutableLiveData<String> = MutableLiveData("init")
        destination.bindStringToTextFieldText(source)
        assertEquals(
            expected = "init",
            actual = destination.text
        )
        source.value = "second"
        assertEquals(
            expected = "second",
            actual = destination.text
        )
    }

    // can't now set uitextfield value as user do
//    @Test
    fun `nonnullable two way string text`() {
        val source: MutableLiveData<String> = MutableLiveData("init")
        destination.bindStringTwoWayToTextFieldText(source)
        assertEquals(
            expected = "init",
            actual = destination.text
        )

        source.value = "second"
        assertEquals(
            expected = "second",
            actual = destination.text
        )

        destination.text = "third"
        assertEquals(
            expected = "third",
            actual = source.value
        )
    }

    @Test
    fun `nullable string text`() {
        val source: MutableLiveData<String?> = MutableLiveData(null)
        destination.bindStringToTextFieldText(source)
        assertEquals(
            expected = "",
            actual = destination.text
        )
        source.value = "value"
        assertEquals(
            expected = "value",
            actual = destination.text
        )
    }
}
