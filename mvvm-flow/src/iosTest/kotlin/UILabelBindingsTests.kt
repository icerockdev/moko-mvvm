/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import dev.icerock.moko.mvvm.flow.binding.bindText
import dev.icerock.moko.mvvm.flow.cStateFlow
import kotlinx.cinterop.readValue
import kotlinx.coroutines.flow.MutableStateFlow
import platform.CoreGraphics.CGRectZero
import platform.UIKit.UILabel
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UILabelBindingsTests {

    private lateinit var destination: UILabel

    @BeforeTest
    fun setup() {
        destination = UILabel(frame = CGRectZero.readValue())
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

    @Test
    fun `nullable string text`() {
        val source: MutableStateFlow<String?> = MutableStateFlow(null)
        destination.bindText(source.cStateFlow())
        assertEquals(
            expected = null,
            actual = destination.text
        )
        source.value = "value"
        assertEquals(
            expected = "value",
            actual = destination.text
        )
    }
}
