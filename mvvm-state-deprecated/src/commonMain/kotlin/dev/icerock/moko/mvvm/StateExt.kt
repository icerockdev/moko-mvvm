/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm

fun <T, E> T.asState(): State<T, E> =
    State.Data(this)

fun <T, E> T?.asState(whenNull: () -> State<T, E>): State<T, E> =
    this?.asState() ?: whenNull()

fun <T, E> List<T>.asState(): State<List<T>, E> = if (this.isEmpty()) {
    State.Empty()
} else {
    State.Data(this)
}

fun <T, E> List<T>?.asState(whenNull: () -> State<List<T>, E>): State<List<T>, E> =
    this?.asState() ?: whenNull()

inline fun <reified T, reified E> State<T, E>?.nullAsEmpty(): State<T, E> =
    this ?: State.Empty()

inline fun <reified T, reified E> State<T, E>?.nullAsLoading(): State<T, E> =
    this ?: State.Loading()
