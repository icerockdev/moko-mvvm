/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.resources.bindText
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kotlinx.cinterop.readValue
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
    fun `nonnullable stringdesc text`() {
        val source: MutableLiveData<StringDesc> = MutableLiveData("init".desc())
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
        val source: MutableLiveData<StringDesc?> = MutableLiveData(null)
        destination.bindText(source)
        assertEquals(
            expected = null,
            actual = destination.text
        )
        source.value = "value".desc()
        assertEquals(
            expected = "value",
            actual = destination.text
        )
    }
}
