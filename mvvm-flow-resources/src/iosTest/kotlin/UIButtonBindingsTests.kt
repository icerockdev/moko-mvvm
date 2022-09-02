/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.flow.resources.bindTitle
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kotlinx.cinterop.readValue
import kotlinx.coroutines.flow.MutableStateFlow
import platform.CoreGraphics.CGRectZero
import platform.UIKit.UIButton
import kotlin.test.BeforeTest
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

@Ignore
class UIButtonBindingsTests {

    private lateinit var destination: UIButton

    @BeforeTest
    fun setup() {
        destination = UIButton(frame = CGRectZero.readValue())
    }

    @Test
    fun `nonnullable stringdesc title`() {
        val source: MutableStateFlow<StringDesc> = MutableStateFlow("init".desc())
        destination.bindTitle(source.cStateFlow())
        assertEquals(
            expected = "init",
            actual = destination.currentTitle
        )
        source.value = "second".desc()
        assertEquals(
            expected = "second",
            actual = destination.currentTitle
        )
    }

    @Test
    fun `nullable stringdesc title`() {
        val source: MutableStateFlow<StringDesc?> = MutableStateFlow(null)
        destination.bindTitle(source.cStateFlow())
        assertEquals(
            expected = null,
            actual = destination.currentTitle
        )
        source.value = "value".desc()
        assertEquals(
            expected = "value",
            actual = destination.currentTitle
        )
    }
}
