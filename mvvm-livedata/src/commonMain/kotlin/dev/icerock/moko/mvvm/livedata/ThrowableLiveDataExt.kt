/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

fun <T : Throwable> LiveData<T>.throwableMessage(mapper: (Throwable) -> String = { it.message.orEmpty() }):
        LiveData<String> = map(mapper)
