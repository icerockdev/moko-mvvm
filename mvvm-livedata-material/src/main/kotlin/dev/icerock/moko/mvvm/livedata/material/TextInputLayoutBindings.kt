/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata.material

import androidx.lifecycle.LifecycleOwner
import com.google.android.material.textfield.TextInputLayout
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.utils.bindNotNull
import dev.icerock.moko.resources.desc.StringDesc

@JvmName("bindToTextInputLayoutErrorString")
fun LiveData<String>.bindToTextInputLayoutError(
    lifecycleOwner: LifecycleOwner,
    textInputLayout: TextInputLayout
) {
    bindNotNull(lifecycleOwner) { textInputLayout.error = it }
}

@JvmName("bindToTextInputLayoutErrorStringDesc")
fun LiveData<StringDesc>.bindToTextInputLayoutError(
    lifecycleOwner: LifecycleOwner,
    textInputLayout: TextInputLayout
) {
    val context = textInputLayout.context
    bindNotNull(lifecycleOwner) { textInputLayout.error = it.toString(context) }
}
