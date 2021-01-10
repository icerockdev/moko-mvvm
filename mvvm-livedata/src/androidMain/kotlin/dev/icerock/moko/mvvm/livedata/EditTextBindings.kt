/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.lifecycle.LifecycleOwner
import dev.icerock.moko.mvvm.utils.bindNotNull

fun MutableLiveData<String>.bindTwoWayToEditTextText(
    lifecycleOwner: LifecycleOwner,
    editText: EditText
) {
    bindNotNull(lifecycleOwner) {
        if (editText.text.toString() == it) return@bindNotNull

        editText.setText(it)
    }

    val liveData = this
    val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val str = s.toString()
            if (str == liveData.value) return

            liveData.value = str
        }

        override fun afterTextChanged(s: Editable?) = Unit
    }
    editText.addTextChangedListener(watcher)
}
