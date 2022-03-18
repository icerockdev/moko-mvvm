/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.sample.declarativeui

import dev.icerock.moko.mvvm.CStateFlow
import dev.icerock.moko.mvvm.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

class BookDetailsViewModel(
    private val bookId: Int
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Loading)
    val state: CStateFlow<State> get() = _state.cStateFlow()

    fun start() = apply { loadBook() }

    fun onRetryPressed() {
        loadBook()
    }

    private fun loadBook() {
        _state.value = State.Loading
        viewModelScope.launch {
            delay(3.seconds)

            val random: Int = Random.nextInt() % 2
            _state.value = when (random) {
                0 -> State.Error("error!".desc())
                1 -> Book.items.first { it.id == bookId }.let { book ->
                    State.Success(
                        title = book.title,
                        author = book.author
                    )
                }
                else -> throw IllegalStateException("invalid random $random")
            }
        }
    }

    sealed interface State {
        object Loading : State
        data class Success(val title: String, val author: String) : State
        data class Error(val message: StringDesc) : State
    }
}
