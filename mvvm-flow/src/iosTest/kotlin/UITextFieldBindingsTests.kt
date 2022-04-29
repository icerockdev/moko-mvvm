/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import dev.icerock.moko.mvvm.flow.binding.bindText
import dev.icerock.moko.mvvm.flow.binding.bindTextTwoWay
import dev.icerock.moko.mvvm.flow.cMutableStateFlow
import dev.icerock.moko.mvvm.flow.cStateFlow
import kotlinx.cinterop.readValue
import kotlinx.coroutines.flow.MutableStateFlow
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
        val source: MutableStateFlow<String> = MutableStateFlow("init")
        destination.bindText(source.cStateFlow())
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
        val source: MutableStateFlow<String> = MutableStateFlow("init")
        destination.bindTextTwoWay(source.cMutableStateFlow())
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
        val source: MutableStateFlow<String?> = MutableStateFlow(null)
        destination.bindText(source.cStateFlow())
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
