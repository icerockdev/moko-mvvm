/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import dev.icerock.moko.mvvm.flow.binding.bindImage
import dev.icerock.moko.mvvm.flow.binding.bindTitle
import dev.icerock.moko.mvvm.flow.cStateFlow
import kotlinx.cinterop.readValue
import kotlinx.coroutines.flow.MutableStateFlow
import platform.CoreGraphics.CGRectZero
import platform.UIKit.UIButton
import platform.UIKit.UIImage
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UIButtonBindingsTests {

    private lateinit var destination: UIButton

    @BeforeTest
    fun setup() {
        destination = UIButton(frame = CGRectZero.readValue())
    }

    @Test
    fun `nonnullable string title`() {
        val source: MutableStateFlow<String> = MutableStateFlow("init")
        destination.bindTitle(source.cStateFlow())
        assertEquals(
            expected = "init",
            actual = destination.currentTitle
        )
        source.value = "second"
        assertEquals(
            expected = "second",
            actual = destination.currentTitle
        )
    }

    @Test
    fun `nullable string title`() {
        val source: MutableStateFlow<String?> = MutableStateFlow(null)
        destination.bindTitle(source.cStateFlow())
        assertEquals(
            expected = null,
            actual = destination.currentTitle
        )
        source.value = "value"
        assertEquals(
            expected = "value",
            actual = destination.currentTitle
        )
    }

    @Test
    fun `bool image`() {
        val source: MutableStateFlow<Boolean> = MutableStateFlow(false)
        val trueImage = UIImage()
        val falseImage = UIImage()
        destination.bindImage(
            flow = source.cStateFlow(),
            trueImage = trueImage,
            falseImage = falseImage
        )
        assertEquals(
            expected = falseImage,
            actual = destination.currentImage
        )
        source.value = true
        assertEquals(
            expected = trueImage,
            actual = destination.currentImage
        )
    }
}
