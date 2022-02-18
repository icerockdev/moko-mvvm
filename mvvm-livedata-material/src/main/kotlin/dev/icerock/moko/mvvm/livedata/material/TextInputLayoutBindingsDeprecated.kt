/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata.material

import androidx.lifecycle.LifecycleOwner
import com.google.android.material.textfield.TextInputLayout
import dev.icerock.moko.mvvm.livedata.Closeable
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.resources.desc.StringDesc

@Deprecated(
    "Use TextInputLayout.bindError",
    replaceWith = ReplaceWith("TextInputLayout.bindError")
)
@JvmName("bindToTextInputLayoutErrorString")
fun LiveData<String>.bindToTextInputLayoutError(
    lifecycleOwner: LifecycleOwner,
    textInputLayout: TextInputLayout
): Closeable {
    return textInputLayout.bindError(lifecycleOwner, this)
}

@Deprecated(
    "Use TextInputLayout.bindError",
    replaceWith = ReplaceWith("TextInputLayout.bindError")
)
@JvmName("bindToTextInputLayoutErrorStringDesc")
fun LiveData<StringDesc>.bindToTextInputLayoutError(
    lifecycleOwner: LifecycleOwner,
    textInputLayout: TextInputLayout
): Closeable {
    return textInputLayout.bindError(lifecycleOwner, this)
}
