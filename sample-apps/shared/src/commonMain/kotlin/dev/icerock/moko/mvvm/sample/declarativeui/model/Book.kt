/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.sample.declarativeui.model

data class Book(
    val id: Int,
    val title: String,
    val author: String
) {
    companion object {
        val items: List<Book> = List(20) { idx ->
            Book(id = idx, title = "Book #$idx", author = "Author of book #$idx")
        }
    }
}
