/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata.glide

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import dev.icerock.moko.mvvm.livedata.Closeable
import dev.icerock.moko.mvvm.livedata.LiveData

@Deprecated(
    "Use ImageView.bindSrc",
    replaceWith = ReplaceWith("ImageView.bindSrc")
)
fun LiveData<String>.bindToImageViewSrc(
    lifecycleOwner: LifecycleOwner,
    imageView: ImageView,
    requestManager: (RequestManager.() -> Unit)? = null,
    requestBuilder: (RequestBuilder<Drawable>.() -> Unit)? = null
): Closeable {
    return imageView.bindSrc(
        lifecycleOwner = lifecycleOwner,
        liveData = this,
        requestManager = requestManager,
        requestBuilder = { it.apply { requestBuilder?.invoke(it) } }
    )
}

@Deprecated(
    "Use ImageView.bindSrc",
    replaceWith = ReplaceWith("ImageView.bindSrc")
)
fun LiveData<String>.bindToImageViewSrc(
    lifecycleOwner: LifecycleOwner,
    imageView: ImageView,
    loadingPlaceholder: Drawable? = null,
    errorPlaceholder: Drawable? = null
): Closeable {
    return imageView.bindSrc(
        lifecycleOwner = lifecycleOwner,
        liveData = this,
        loadingPlaceholder = loadingPlaceholder,
        errorPlaceholder = errorPlaceholder
    )
}
