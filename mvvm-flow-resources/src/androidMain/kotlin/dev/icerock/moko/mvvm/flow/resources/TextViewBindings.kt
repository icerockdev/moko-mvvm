/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.flow.resources

import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import dev.icerock.moko.mvvm.flow.CStateFlow
import dev.icerock.moko.mvvm.flow.binding.bind
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.DisposableHandle

fun TextView.bindText(
    lifecycleOwner: LifecycleOwner,
    flow: CStateFlow<StringDesc?>
): DisposableHandle {
    return flow.bind(lifecycleOwner) { this.text = it?.toString(this.context) }
}
