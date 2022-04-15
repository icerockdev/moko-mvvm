/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.sample.declarativeui

import dev.icerock.moko.mvvm.flow.CFlow
import dev.icerock.moko.mvvm.flow.CStateFlow
import dev.icerock.moko.mvvm.flow.cFlow
import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.sample.declarativeui.model.Advertisement
import dev.icerock.moko.mvvm.sample.declarativeui.model.Book
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

/**
 * Sample ViewModel with state in single StateFlow (mapped to class) and actions by Flow
 * (also mapped to class)
 *
 * Notes:
 * - Flow and StateFlow lost generics on Swift side (because it's interfaces), so we should convert
 *   it to classes - it was done by cStateFlow and cFlow extensions. CFlow and CStateFlow it's
 *   generic classes that can be used from Swift without problems
 * - Flow and StateFlow can be observed with Jetpack Compose out of box. For SwiftUI required
 *   multiple utils functions - binding/state on Swift
 * - To use exhaust swift enum we should use moko-kswift plugin
 */
class BookListViewModel : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Loading)
    val state: CStateFlow<State> get() = _state.cStateFlow()

    private val _actions = Channel<Action>(Channel.BUFFERED)
    val actions: CFlow<Action> get() = _actions.receiveAsFlow().cFlow()

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
        _actions.trySend(Action.RouteToBookDetails(book.id))
    }

    private fun onAdvertisementPressed(advert: Advertisement) {
        _actions.trySend(Action.OpenUrl(advert.url))
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
