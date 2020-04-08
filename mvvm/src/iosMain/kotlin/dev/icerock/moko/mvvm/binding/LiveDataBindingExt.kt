/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */
package dev.icerock.moko.mvvm.binding

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc

fun <T> LiveData<T>.descOrEmpty(): LiveData<StringDesc> {
    return this.map {
        when (it) {
            is StringDesc -> it
            is StringResource -> it.desc()
            is String -> it.desc()
            else -> "".desc()
        }
    }
}

fun <T> LiveData<T>.stringOrEmpty(): LiveData<String> {
    return this.map { (it as? String).orEmpty() }
}