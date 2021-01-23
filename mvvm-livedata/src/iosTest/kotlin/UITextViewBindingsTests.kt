/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.bindStringDescToButtonTitle
import dev.icerock.moko.mvvm.livedata.bindStringDescToLabelText
import dev.icerock.moko.mvvm.livedata.bindStringDescToTextFieldText
import dev.icerock.moko.mvvm.livedata.bindStringDescToTextViewText
import dev.icerock.moko.mvvm.livedata.bindStringToButtonTitle
import dev.icerock.moko.mvvm.livedata.bindStringToLabelText
import dev.icerock.moko.mvvm.livedata.bindStringToTextFieldText
import dev.icerock.moko.mvvm.livedata.bindStringToTextViewText
import dev.icerock.moko.mvvm.livedata.bindStringTwoWayToTextFieldText
import dev.icerock.moko.mvvm.livedata.bindStringTwoWayToTextViewText
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGRectZero
import platform.UIKit.UIButton
import platform.UIKit.UILabel
import platform.UIKit.UITextField
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
        source.bindStringToTextViewText(destination)
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
        source.bindStringTwoWayToTextViewText(destination)
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
        source.bindStringToTextViewText(destination)
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

    @Test
    fun `nonnullable stringdesc text`() {
        val source: MutableLiveData<StringDesc> = MutableLiveData("init".desc())
        source.bindStringDescToTextViewText(destination)
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
        val source: MutableLiveData<StringDesc?> = MutableLiveData(null)
        source.bindStringDescToTextViewText(destination)
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
