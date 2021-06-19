/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

@Suppress("UNCHECKED_CAST")
fun <T1, T2, R> mediatorOf(
    source1: LiveData<T1>,
    source2: LiveData<T2>,
    function: (T1, T2) -> R
): LiveData<R> =
    listOf(source1, source2).mediator { values ->
        @Suppress("MagicNumber")
        function(
            values[0] as T1,
            values[1] as T2,
        )
    }

@Suppress("UNCHECKED_CAST")
fun <T1, T2, T3, R> mediatorOf(
    source1: LiveData<T1>,
    source2: LiveData<T2>,
    source3: LiveData<T3>,
    function: (T1, T2, T3) -> R
): LiveData<R> =
    listOf(source1, source2, source3).mediator { values ->
        @Suppress("MagicNumber")
        function(
            values[0] as T1,
            values[1] as T2,
            values[2] as T3,
        )
    }

@Suppress("UNCHECKED_CAST")
fun <T1, T2, T3, T4, R> mediatorOf(
    source1: LiveData<T1>,
    source2: LiveData<T2>,
    source3: LiveData<T3>,
    source4: LiveData<T4>,
    function: (T1, T2, T3, T4) -> R
): LiveData<R> =
    listOf(source1, source2, source3, source4).mediator { values ->
        @Suppress("MagicNumber")
        function(
            values[0] as T1,
            values[1] as T2,
            values[2] as T3,
            values[3] as T4,
        )
    }

@Suppress("UNCHECKED_CAST", "LongParameterList")
fun <T1, T2, T3, T4, T5, R> mediatorOf(
    source1: LiveData<T1>,
    source2: LiveData<T2>,
    source3: LiveData<T3>,
    source4: LiveData<T4>,
    source5: LiveData<T5>,
    function: (T1, T2, T3, T4, T5) -> R
): LiveData<R> =
    listOf(source1, source2, source3, source4, source5).mediator { values ->
        @Suppress("MagicNumber")
        function(
            values[0] as T1,
            values[1] as T2,
            values[2] as T3,
            values[3] as T4,
            values[4] as T5,
        )
    }

@Suppress("UNCHECKED_CAST", "LongParameterList")
fun <T1, T2, T3, T4, T5, T6, R> mediatorOf(
    source1: LiveData<T1>,
    source2: LiveData<T2>,
    source3: LiveData<T3>,
    source4: LiveData<T4>,
    source5: LiveData<T5>,
    source6: LiveData<T6>,
    function: (T1, T2, T3, T4, T5, T6) -> R
): LiveData<R> =
    listOf(
        source1,
        source2,
        source3,
        source4,
        source5,
        source6,
    ).mediator { values ->
        @Suppress("MagicNumber")
        function(
            values[0] as T1,
            values[1] as T2,
            values[2] as T3,
            values[3] as T4,
            values[4] as T5,
            values[5] as T6,
        )
    }

@Suppress("UNCHECKED_CAST", "LongParameterList")
fun <T1, T2, T3, T4, T5, T6, T7, R> mediatorOf(
    source1: LiveData<T1>,
    source2: LiveData<T2>,
    source3: LiveData<T3>,
    source4: LiveData<T4>,
    source5: LiveData<T5>,
    source6: LiveData<T6>,
    source7: LiveData<T7>,
    function: (T1, T2, T3, T4, T5, T6, T7) -> R
): LiveData<R> =
    listOf(
        source1,
        source2,
        source3,
        source4,
        source5,
        source6,
        source7,
    ).mediator { values ->
        @Suppress("MagicNumber")
        function(
            values[0] as T1,
            values[1] as T2,
            values[2] as T3,
            values[3] as T4,
            values[4] as T5,
            values[5] as T6,
            values[6] as T7,
        )
    }

@Suppress("UNCHECKED_CAST", "LongParameterList")
fun <T1, T2, T3, T4, T5, T6, T7, T8, R> mediatorOf(
    source1: LiveData<T1>,
    source2: LiveData<T2>,
    source3: LiveData<T3>,
    source4: LiveData<T4>,
    source5: LiveData<T5>,
    source6: LiveData<T6>,
    source7: LiveData<T7>,
    source8: LiveData<T8>,
    function: (T1, T2, T3, T4, T5, T6, T7, T8) -> R
): LiveData<R> =
    listOf(
        source1,
        source2,
        source3,
        source4,
        source5,
        source6,
        source7,
        source8,
    ).mediator { values ->
        @Suppress("MagicNumber")
        function(
            values[0] as T1,
            values[1] as T2,
            values[2] as T3,
            values[3] as T4,
            values[4] as T5,
            values[5] as T6,
            values[6] as T7,
            values[7] as T8,
        )
    }

@Suppress("UNCHECKED_CAST", "LongParameterList")
fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> mediatorOf(
    source1: LiveData<T1>,
    source2: LiveData<T2>,
    source3: LiveData<T3>,
    source4: LiveData<T4>,
    source5: LiveData<T5>,
    source6: LiveData<T6>,
    source7: LiveData<T7>,
    source8: LiveData<T8>,
    source9: LiveData<T9>,
    function: (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> R
): LiveData<R> =
    listOf(
        source1,
        source2,
        source3,
        source4,
        source5,
        source6,
        source7,
        source8,
        source9,
    ).mediator { values ->
        @Suppress("MagicNumber")
        function(
            values[0] as T1,
            values[1] as T2,
            values[2] as T3,
            values[3] as T4,
            values[4] as T5,
            values[5] as T6,
            values[6] as T7,
            values[7] as T8,
            values[8] as T9,
        )
    }

@Suppress("UNCHECKED_CAST", "LongParameterList")
fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> mediatorOf(
    source1: LiveData<T1>,
    source2: LiveData<T2>,
    source3: LiveData<T3>,
    source4: LiveData<T4>,
    source5: LiveData<T5>,
    source6: LiveData<T6>,
    source7: LiveData<T7>,
    source8: LiveData<T8>,
    source9: LiveData<T9>,
    source10: LiveData<T10>,
    function: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> R
): LiveData<R> =
    listOf(
        source1,
        source2,
        source3,
        source4,
        source5,
        source6,
        source7,
        source8,
        source9,
        source10
    ).mediator { values ->
        @Suppress("MagicNumber")
        function(
            values[0] as T1,
            values[1] as T2,
            values[2] as T3,
            values[3] as T4,
            values[4] as T5,
            values[5] as T6,
            values[6] as T7,
            values[7] as T8,
            values[8] as T9,
            values[9] as T10,
        )
    }
