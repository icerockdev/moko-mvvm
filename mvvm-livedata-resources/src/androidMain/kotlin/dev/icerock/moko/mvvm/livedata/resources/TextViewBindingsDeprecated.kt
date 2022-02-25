/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata.resources

import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import dev.icerock.moko.mvvm.livedata.Closeable
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.resources.desc.StringDesc

@Deprecated(
    "Use TextView.bindText",
    replaceWith = ReplaceWith("TextView.bindText")
)
fun LiveData<StringDesc>.bindToTextViewText(
    lifecycleOwner: LifecycleOwner,
    textView: TextView
): Closeable {
    return textView.bindText(lifecycleOwner, this)
}
