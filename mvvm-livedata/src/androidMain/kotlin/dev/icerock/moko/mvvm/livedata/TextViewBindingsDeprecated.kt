/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import android.widget.TextView
import androidx.lifecycle.LifecycleOwner

@Deprecated(
    "Use TextView.bindText",
    replaceWith = ReplaceWith("TextView.bindText")
)
fun LiveData<String>.bindToTextViewText(
    lifecycleOwner: LifecycleOwner,
    textView: TextView
): Closeable {
    return textView.bindText(lifecycleOwner, this)
}
