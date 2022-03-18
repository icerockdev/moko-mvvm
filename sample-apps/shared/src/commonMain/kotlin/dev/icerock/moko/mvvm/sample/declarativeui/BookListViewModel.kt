/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.sample.declarativeui

import dev.icerock.moko.mvvm.CFlow
import dev.icerock.moko.mvvm.CStateFlow
import dev.icerock.moko.mvvm.cFlow
import dev.icerock.moko.mvvm.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

class BookListViewModel : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Loading)
    val state: CStateFlow<State> get() = _state.cStateFlow()

    private val _actions = MutableSharedFlow<Action>()
    val actions: CFlow<Action> get() = _actions.cFlow()

    fun start() = apply { loadBooks() }

    fun onRetryPressed() {
        loadBooks()
    }

    private fun loadBooks() {
        _state.value = State.Loading
        viewModelScope.launch {
            delay(3.seconds)

            val random: Int = abs(Random.nextInt() % 3)
            _state.value = when (random) {
                0 -> State.Empty("items not found!".desc())
                1 -> State.Error("error!".desc())
                2 -> State.Success(
                    items = (Book.items.map { book ->
                        ListUnit.BookUnit(
                            id = "book-" + book.id,
                            title = book.title
                        ) { onBookPressed(book) }
                    } + Advertisement.items.map { advert ->
                        ListUnit.AdvertUnit(
                            id = "advert-" + advert.id,
                            text = advert.text
                        ) { onAdvertisementPressed(advert) }
                    }).shuffled()
                )
                else -> throw IllegalStateException("invalid random $random")
            }
        }
    }

    private fun onBookPressed(book: Book) {
        println("pressed $book")
        viewModelScope.launch {
            println("before")
            _actions.emit(Action.RouteToBookDetails(book.id))
            println("after")
        }
    }

    private fun onAdvertisementPressed(advert: Advertisement) {
        println("pressed $advert")
        viewModelScope.launch {
            println("before")
            _actions.emit(Action.OpenUrl(advert.url))
            println("after")
        }
    }

    sealed interface State {
        object Loading : State
        data class Empty(val message: StringDesc) : State
        data class Success(val items: List<ListUnit>) : State
        data class Error(val message: StringDesc) : State
    }

    sealed interface ListUnit {
        val id: String

        data class BookUnit(
            override val id: String,
            val title: String,
            val onPressed: () -> Unit
        ) : ListUnit

        data class AdvertUnit(
            override val id: String,
            val text: String,
            val onPressed: () -> Unit
        ) : ListUnit
    }

    sealed interface Action {
        data class RouteToBookDetails(val id: Int) : Action
        data class OpenUrl(val url: String) : Action
    }
}
