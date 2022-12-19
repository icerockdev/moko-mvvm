/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.flow.material

import androidx.lifecycle.LifecycleOwner
import com.google.android.material.textfield.TextInputLayout
import dev.icerock.moko.mvvm.flow.CStateFlow
import dev.icerock.moko.mvvm.flow.binding.bind
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.DisposableHandle

@JvmName("bindErrorString")
fun <T : String?> TextInputLayout.bindError(
    lifecycleOwner: LifecycleOwner,
    flow: CStateFlow<T?>
): DisposableHandle {
    return flow.bind(lifecycleOwner) {
        error = it
        isErrorEnabled = it != null
    }
}

@JvmName("bindErrorStringDesc")
fun <T : StringDesc?> TextInputLayout.bindError(
    lifecycleOwner: LifecycleOwner,
    flow: CStateFlow<T?>
): DisposableHandle {
    return flow.bind(lifecycleOwner) {
        error = it?.toString(context)
        isErrorEnabled = it != null
    }
}
