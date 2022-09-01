/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import dev.icerock.moko.mvvm.flow.CMutableStateFlow
import dev.icerock.moko.mvvm.flow.cMutableStateFlow
import dev.icerock.moko.mvvm.flow.resources.bindText
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kotlinx.cinterop.readValue
import kotlinx.coroutines.flow.MutableStateFlow
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
    fun `nonnullable stringdesc text`() {
        val source: CMutableStateFlow<StringDesc> = MutableStateFlow<StringDesc>("init".desc()).cMutableStateFlow()
        destination.bindText(source)
        assertEquals(
            expected = "init",
            actual = destination.text
        )
        source.value = "second".desc()
        assertEquals(
            expected = "second",
            actual = destination.text
        )
    }

    @Test
    fun `nullable stringdesc text`() {
        val source: CMutableStateFlow<StringDesc?> = MutableStateFlow<StringDesc?>(null).cMutableStateFlow()
        destination.bindText(source)
        assertEquals(
            expected = "",
            actual = destination.text
        )
        source.value = "value".desc()
        assertEquals(
            expected = "value",
            actual = destination.text
        )
    }
}