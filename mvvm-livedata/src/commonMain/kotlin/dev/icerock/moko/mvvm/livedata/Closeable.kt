/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

fun interface Closeable {
    fun close()

    operator fun plus(other: Closeable): Closeable {
        return Closeable {
            this.close()
            other.close()
        }
    }
}
