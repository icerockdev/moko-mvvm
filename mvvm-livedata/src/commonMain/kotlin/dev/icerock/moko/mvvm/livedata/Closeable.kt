/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

interface Closeable {
    fun close()

    operator fun plus(other: Closeable): Closeable {
        return Closeable {
            this.close()
            other.close()
        }
    }

    companion object {
        operator fun invoke(block: () -> Unit): Closeable {
            return object : Closeable {
                override fun close() {
                    block()
                }
            }
        }
    }
}
