/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.bindBoolToButtonImage
import dev.icerock.moko.mvvm.livedata.bindStringToButtonTitle
import kotlinx.cinterop.readValue
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
        val source: MutableLiveData<String> = MutableLiveData("init")
        destination.bindStringToButtonTitle(source)
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
        val source: MutableLiveData<String?> = MutableLiveData(null)
        destination.bindStringToButtonTitle(source)
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
        val source: MutableLiveData<Boolean> = MutableLiveData(false)
        val trueImage = UIImage()
        val falseImage = UIImage()
        destination.bindBoolToButtonImage(
            liveData = source,
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
