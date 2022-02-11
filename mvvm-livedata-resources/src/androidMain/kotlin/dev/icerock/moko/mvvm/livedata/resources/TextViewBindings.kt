/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata.resources

import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import dev.icerock.moko.mvvm.livedata.Closeable
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.utils.bindNotNull
import dev.icerock.moko.resources.desc.StringDesc

fun LiveData<StringDesc>.bindToTextViewText(
    lifecycleOwner: LifecycleOwner,
    textView: TextView
): Closeable {
    val context = textView.context
    return bindNotNull(lifecycleOwner) { textView.text = it.toString(context) }
}
