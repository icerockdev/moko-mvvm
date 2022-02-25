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

fun ImageView.bindSrc(
    lifecycleOwner: LifecycleOwner,
    liveData: LiveData<String>,
    requestManager: (RequestManager.() -> Unit)? = null,
    requestBuilder: ((RequestBuilder<Drawable>) -> RequestBuilder<Drawable>) = { it }
): Closeable {
    return liveData.bindNotNull(lifecycleOwner) { url ->
        Glide.with(this)
            .also { requestManager?.invoke(it) }
            .load(url)
            .let { requestBuilder.invoke(it) }
            .into(this)
    }
}

fun ImageView.bindSrc(
    lifecycleOwner: LifecycleOwner,
    liveData: LiveData<String>,
    loadingPlaceholder: Drawable? = null,
    errorPlaceholder: Drawable? = null
): Closeable {
    return bindSrc(
        lifecycleOwner = lifecycleOwner,
        liveData = liveData,
        requestBuilder = {
            it.placeholder(loadingPlaceholder).error(errorPlaceholder)
        }
    )
}
