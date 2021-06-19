/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.utils.bind
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGRectZero
import platform.UIKit.UILabel
import platform.UIKit.UIViewController
import platform.UIKit.removeFromSuperview
import kotlin.test.Test
import kotlin.test.assertEquals

class BindTests {

    @Test
    fun `bind livedata lazy lifecycle start`() {
        val label = UILabel(frame = CGRectZero.readValue())
        val window = label.wrapIntoWindow()
        val source: MutableLiveData<String> = MutableLiveData(initialValue = "test")
        var lastValue: String? = null

        source.bind(view = label) { value ->
            this.text = value
            lastValue = value
        }

        assertEquals(expected = null, actual = label.text)
        assertEquals(expected = null, actual = lastValue)

        window.makeKeyAndVisible()

        assertEquals(expected = "test", actual = label.text)
        assertEquals(expected = "test", actual = lastValue)

        source.value = "test2"
        assertEquals(expected = "test2", actual = label.text)
        assertEquals(expected = "test2", actual = lastValue)

        label.removeFromSuperview()

        source.value = "test3"
        assertEquals(expected = "test2", actual = label.text)
        assertEquals(expected = "test2", actual = lastValue)
    }

    @Test
    fun `bind livedata lifecycle pre start`() {
        val label = UILabel(frame = CGRectZero.readValue())
        val window = label.wrapIntoWindow()
        val source: MutableLiveData<String> = MutableLiveData(initialValue = "test")
        var lastValue: String? = null

        window.makeKeyAndVisible()

        source.bind(view = label) { value ->
            this.text = value
            lastValue = value
        }

        assertEquals(expected = "test", actual = label.text)
        assertEquals(expected = "test", actual = lastValue)

        source.value = "test2"
        assertEquals(expected = "test2", actual = label.text)
        assertEquals(expected = "test2", actual = lastValue)

        window.rootViewController = UIViewController()

        source.value = "test3"
        assertEquals(expected = "test2", actual = label.text)
        assertEquals(expected = "test2", actual = lastValue)
    }
}
