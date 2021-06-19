/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.bindBoolToViewBackgroundColor
import dev.icerock.moko.mvvm.livedata.bindBoolToViewHidden
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGRectZero
import platform.UIKit.UIColor
import platform.UIKit.UIView
import platform.UIKit.backgroundColor
import platform.UIKit.isHidden
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UIViewBindingsTests {

    private lateinit var destination: UIView

    @BeforeTest
    fun setup() {
        destination = UIView(frame = CGRectZero.readValue())
        destination.wrapIntoWindow().makeKeyAndVisible()
    }

    @Test
    fun `bool hidden`() {
        val source: MutableLiveData<Boolean> = MutableLiveData(false)
        source.bindBoolToViewHidden(destination)
        assertEquals(
            expected = false,
            actual = destination.isHidden()
        )
        source.value = true
        assertEquals(
            expected = true,
            actual = destination.isHidden()
        )
    }

    @Test
    fun `bool color`() {
        val source: MutableLiveData<Boolean> = MutableLiveData(false)
        val trueColor = UIColor.blueColor
        val falseColor = UIColor.redColor
        source.bindBoolToViewBackgroundColor(
            view = destination,
            trueColor = trueColor,
            falseColor = falseColor
        )
        assertEquals(
            expected = falseColor,
            actual = destination.backgroundColor
        )
        source.value = true
        assertEquals(
            expected = trueColor,
            actual = destination.backgroundColor
        )
    }
}
