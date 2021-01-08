/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.databinding

import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import dev.icerock.moko.resources.desc.StringDesc

object StringDescAdapters {
    @JvmStatic
    @BindingAdapter("android:text")
    fun setText(textView: TextView, stringDesc: StringDesc?) {
        textView.text = stringDesc?.toString(textView.context)
    }

    @JvmStatic
    @BindingAdapter("android:text")
    fun setText(button: Button, stringDesc: StringDesc?) {
        button.text = stringDesc?.toString(button.context)
    }

    @JvmStatic
    @BindingAdapter("error")
    fun setError(textInputLayout: TextInputLayout, stringDesc: StringDesc?) {
        textInputLayout.error = stringDesc?.toString(textInputLayout.context)
    }
}
