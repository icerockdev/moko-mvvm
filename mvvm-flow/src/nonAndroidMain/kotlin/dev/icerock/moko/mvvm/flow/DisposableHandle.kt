/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.flow

// just to not export all coroutines api to swift side
interface DisposableHandle : kotlinx.coroutines.DisposableHandle

fun DisposableHandle(block: () -> Unit): DisposableHandle {
    return object : DisposableHandle {
        override fun dispose() {
            block()
        }
    }
}

operator fun DisposableHandle.plus(other: DisposableHandle): DisposableHandle {
    return DisposableHandle {
        this.dispose()
        other.dispose()
    }
}
