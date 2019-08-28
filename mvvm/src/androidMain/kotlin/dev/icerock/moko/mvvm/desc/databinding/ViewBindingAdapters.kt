/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.desc.databinding

import android.view.View
import androidx.databinding.BindingAdapter

object ViewBindingAdapters {
    @JvmStatic
    @BindingAdapter("invisibleOrGone")
    fun setInvisibleOrGone(view: View, value: Boolean) {
        view.visibility = if (value) View.INVISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("visibleOrGone")
    fun setVisibleOrGone(view: View, value: Boolean) {
        view.visibility = if (value) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("visibleOrInvisible")
    fun setVisibleOrInvisible(view: View, value: Boolean) {
        view.visibility = if (value) View.VISIBLE else View.INVISIBLE
    }
}
