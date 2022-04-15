/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.sample.declarativeui.android

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher

// TODO change this logic...maybe remove eventsdispatcher usage at all
//  https://developer.android.com/jetpack/compose/interop/interop-apis
@Composable
fun <T : Any> EventsDispatcher<T>.bindToComposable(listener: T) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val eventsDispatcher: EventsDispatcher<T> = this

    DisposableEffect(key1 = eventsDispatcher) {
        eventsDispatcher.bind(lifecycleOwner, listener)

        onDispose {
            // here we should unbind
        }
    }
}
