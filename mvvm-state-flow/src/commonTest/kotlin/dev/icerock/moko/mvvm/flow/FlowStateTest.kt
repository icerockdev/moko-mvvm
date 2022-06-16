/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.flow


import dev.icerock.moko.mvvm.test.flow.FlowTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestResult
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class Test : FlowTest() {
    @Test
    override fun dataTransformTest(): TestResult {
        return super.dataTransformTest()
    }

    @Test
    override fun dataTransformMergeWithTest(): TestResult {
        return super.dataTransformMergeWithTest()
    }
}