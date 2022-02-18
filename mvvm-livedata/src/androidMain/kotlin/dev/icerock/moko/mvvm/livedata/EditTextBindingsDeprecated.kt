/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import android.widget.EditText
import androidx.lifecycle.LifecycleOwner

@Deprecated(
    "Use EditText.bindTextTwoWay",
    replaceWith = ReplaceWith("EditText.bindTextTwoWay")
)
fun MutableLiveData<String>.bindTwoWayToEditTextText(
    lifecycleOwner: LifecycleOwner,
    editText: EditText
): Closeable {
    return editText.bindTextTwoWay(lifecycleOwner, this)
}
