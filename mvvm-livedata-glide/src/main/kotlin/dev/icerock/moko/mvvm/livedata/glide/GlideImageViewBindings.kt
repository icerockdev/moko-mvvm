/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata.glide

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import dev.icerock.moko.mvvm.livedata.Closeable
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.utils.bindNotNull

fun LiveData<String>.bindToImageViewSrc(
    lifecycleOwner: LifecycleOwner,
    imageView: ImageView,
    requestManager: (RequestManager.() -> Unit)? = null,
    requestBuilder: (RequestBuilder<Drawable>.() -> Unit)? = null
): Closeable {
    return bindNotNull(lifecycleOwner) { url ->
        Glide.with(imageView)
            .also { requestManager?.invoke(it) }
            .load(url)
            .also { requestBuilder?.invoke(it) }
            .into(imageView)
    }
}

fun LiveData<String>.bindToImageViewSrc(
    lifecycleOwner: LifecycleOwner,
    imageView: ImageView,
    loadingPlaceholder: Drawable? = null,
    errorPlaceholder: Drawable? = null
): Closeable {
    return bindToImageViewSrc(
        lifecycleOwner = lifecycleOwner,
        imageView = imageView,
        requestBuilder = {
            placeholder(loadingPlaceholder)
            error(errorPlaceholder)
        }
    )
}
