/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.internal

actual class WeakReference<T : Any> actual constructor(private val referred: T) {
    actual fun get(): T? = referred
}
