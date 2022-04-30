/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.sample.declarativeui.android

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.icerock.moko.mvvm.createViewModelFactory
import dev.icerock.moko.mvvm.flow.compose.observeAsActions
import dev.icerock.moko.mvvm.sample.declarativeui.BookListViewModel
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc

@Composable
fun BookListScreen(
    viewModel: BookListViewModel = viewModel(
        factory = createViewModelFactory { BookListViewModel().start() }
    ),
    onOpenBook: (Int) -> Unit = {}
) {
    val state: BookListViewModel.State by viewModel.state.collectAsState()
    val context: Context = LocalContext.current

    viewModel.actions.observeAsActions { it.handleAction(context, onOpenBook) }

    when (@Suppress("NAME_SHADOWING") val state = state) {
        is BookListViewModel.State.Empty -> EmptyState(message = state.message)
        is BookListViewModel.State.Error -> ErrorState(message = state.message)
        BookListViewModel.State.Loading -> LoadingState()
        is BookListViewModel.State.Success -> SuccessState(items = state.items)
    }
}

private fun BookListViewModel.Action.handleAction(
    context: Context,
    onOpenBook: (Int) -> Unit
) {
    when (this) {
        is BookListViewModel.Action.OpenUrl -> {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(this.url))
            context.startActivity(intent)
        }
        is BookListViewModel.Action.RouteToBookDetails -> onOpenBook(this.id)
    }
}

@Composable
private fun EmptyState(message: StringDesc) {
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                imageVector = Icons.Default.Clear,
                contentDescription = null,
                modifier = Modifier.size(128.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = message.localized())
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun EmptyStatePreview() {
    EmptyState(message = "no items".desc())
}

@Composable
private fun ErrorState(message: StringDesc) {
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                imageVector = Icons.Filled.Warning,
                contentDescription = null,
                modifier = Modifier.size(128.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = message.localized(), color = Color.Red)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ErrorStatePreview() {
    ErrorState(message = "No internet :(".desc())
}

@Composable
private fun LoadingState() {
    Box(Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoadingStatePreview() {
    LoadingState()
}

@Composable
private fun SuccessState(items: List<BookListViewModel.ListUnit>) {
    LazyColumn(Modifier.fillMaxSize()) {
        items(items, key = { it.id }) { item ->
            when (item) {
                is BookListViewModel.ListUnit.AdvertUnit -> AdvertUnit(item)
                is BookListViewModel.ListUnit.BookUnit -> BookUnit(item)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BookUnit(unit: BookListViewModel.ListUnit.BookUnit) {
    ListItem(
        text = { Text(unit.title) },
        trailing = {
            Box(modifier = Modifier.fillMaxHeight()) {
                Icon(
                    Icons.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        modifier = Modifier.clickable { unit.onPressed() }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AdvertUnit(unit: BookListViewModel.ListUnit.AdvertUnit) {
    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            onClick = { unit.onPressed() }
        ) {
            Text(modifier = Modifier.padding(8.dp), text = unit.text, textAlign = TextAlign.Center)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SuccessStatePreview() {
    SuccessState(
        items = listOf(
            BookListViewModel.ListUnit.BookUnit(
                id = "1",
                title = "1984",
                onPressed = {}
            ),
            BookListViewModel.ListUnit.BookUnit(
                id = "2",
                title = "iOS Development",
                onPressed = {}
            ),
            BookListViewModel.ListUnit.AdvertUnit(
                id = "3",
                text = "This advert!",
                onPressed = {}
            ),
            BookListViewModel.ListUnit.BookUnit(
                id = "4",
                title = "Android Development",
                onPressed = {}
            ),
            BookListViewModel.ListUnit.AdvertUnit(
                id = "5",
                text = "This advert 2!",
                onPressed = {}
            )
        )
    )
}
