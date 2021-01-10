/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import android.content.Context
import dev.icerock.moko.resources.desc.StringDesc

@JvmName("mapToStringStringDesc")
fun LiveData<StringDesc>.mapToString(context: Context): LiveData<String> {
    return map { it.toString(context) }
}

@JvmName("mapToStringStringDescOptional")
fun LiveData<StringDesc?>.mapToString(context: Context): LiveData<String?> {
    return map { it?.toString(context) }
}
