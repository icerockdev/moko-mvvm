/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.flow.binding.bindText
import dev.icerock.moko.mvvm.flow.binding.bindTextTwoWay
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGRectZero
import platform.UIKit.UITextView
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UITextViewBindingsTests {

    private lateinit var destination: UITextView

    @BeforeTest
    fun setup() {
        destination = UITextView(frame = CGRectZero.readValue())
    }

    @Test
    fun `nonnullable string text`() {
        val source: MutableLiveData<String> = MutableLiveData("init")
        destination.bindText(source)
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

    // can't now set uitextview value as user do
//    @Test
    fun `nonnullable two way string text`() {
        val source: MutableLiveData<String> = MutableLiveData("init")
        destination.bindTextTwoWay(source)
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
        destination.bindText(source)
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
