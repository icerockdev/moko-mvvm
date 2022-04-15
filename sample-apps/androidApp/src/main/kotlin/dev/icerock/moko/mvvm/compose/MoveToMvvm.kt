/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

inline fun <reified T : ViewModel> viewModelFactory(crossinline block: () -> T): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return block() as T
        }
    }
}
//
//@Composable
//fun <T> LiveData<T>.observeAsState(): State<T> {
//    return ld().observeAsState(initial = this.value)
//}

@Composable
fun <T> Flow<T>.observeAsActions(onEach: (T) -> Unit) {
    val flow = this
    LaunchedEffect(key1 = flow) {
        flow.onEach(onEach).collect()
    }
}
