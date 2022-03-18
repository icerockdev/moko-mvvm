/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.sample.declarativeui

data class Advertisement(
    val id: Int,
    val text: String,
    val url: String
) {
    companion object {
        val items: List<Advertisement> = List(5) { idx ->
            Advertisement(id = idx, text = "Here ad $idx", url = "https://icerock.dev")
        }
    }
}
