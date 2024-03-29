/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.sample.declarativeui.android

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.icerock.moko.mvvm.createViewModelFactory
import dev.icerock.moko.mvvm.flow.compose.observeAsActions
import dev.icerock.moko.mvvm.sample.declarativeui.BookReviewViewModel
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc

@Composable
fun BookReviewScreen(
    bookId: Int,
    viewModel: BookReviewViewModel = viewModel(
        factory = createViewModelFactory { BookReviewViewModel(bookId) }
    ),
    onCloseScreen: () -> Unit = {}
) {
    val state: BookReviewViewModel.State by viewModel.state.collectAsState()

    viewModel.actions.observeAsActions { it.handleAction(onCloseScreen) }

    when (@Suppress("NAME_SHADOWING") val state = state) {
        is BookReviewViewModel.State.Error -> ErrorState(
            inputForm = state.form,
            message = state.message,
            onClose = { viewModel.onErrorClosed() }
        )
        is BookReviewViewModel.State.Idle -> IdleState(
            inputForm = state.form,
            onRateChanged = { viewModel.onRateChange(it) },
            onMessageChanged = { viewModel.onMessageChange(it) },
            onSubmit = { viewModel.onSendPressed() }
        )
        is BookReviewViewModel.State.Loading -> LoadingState(
            inputForm = state.form
        )
    }
}

private fun BookReviewViewModel.Action.handleAction(
    onCloseScreen: () -> Unit
) {
    when (this) {
        BookReviewViewModel.Action.CloseScreen -> onCloseScreen()
    }
}

@Composable
private fun ReviewInputForm(
    inputForm: BookReviewViewModel.InputForm,
    onRateChanged: ((Int) -> Unit)? = null,
    onMessageChanged: ((String) -> Unit)? = null,
    onSubmit: (() -> Unit)? = null
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            for (i in 1..5) {
                if (i != 1) {
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Button(
                    onClick = { onRateChanged?.invoke(i) },
                    enabled = onRateChanged != null,
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = if (inputForm.rate == i) MaterialTheme.colors.secondary
                        else MaterialTheme.colors.surface
                    )
                ) {
                    Text(text = i.toString())
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = inputForm.message,
            enabled = onMessageChanged != null,
            onValueChange = { onMessageChanged?.invoke(it) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onSubmit?.invoke() },
            enabled = onSubmit != null
        ) {
            Text(text = "Send")
        }
    }
}

@Composable
private fun IdleState(
    inputForm: BookReviewViewModel.InputForm,
    onRateChanged: (Int) -> Unit,
    onMessageChanged: (String) -> Unit,
    onSubmit: () -> Unit
) {
    ReviewInputForm(
        inputForm = inputForm,
        onRateChanged = onRateChanged,
        onMessageChanged = onMessageChanged,
        onSubmit = onSubmit
    )
}

@Preview(showSystemUi = true)
@Composable
private fun IdleStatePreview() {
    IdleState(
        inputForm = BookReviewViewModel.InputForm(rate = 3, message = "hello"),
        onRateChanged = {},
        onMessageChanged = {},
        onSubmit = {}
    )
}

@Composable
private fun ErrorState(
    inputForm: BookReviewViewModel.InputForm,
    message: StringDesc,
    onClose: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        ReviewInputForm(inputForm)
        AlertDialog(
            title = { Text("Error") },
            text = { Text(message.localized()) },
            onDismissRequest = onClose,
            confirmButton = {
                Button(onClick = onClose) { Text(text = "Close") }
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ErrorStatePreview() {
    ErrorState(
        inputForm = BookReviewViewModel.InputForm(rate = 3, message = "hello"),
        message = "fail to load!".desc(),
        onClose = {}
    )
}

@Composable
private fun LoadingState(inputForm: BookReviewViewModel.InputForm) {
    Box(modifier = Modifier.fillMaxSize()) {
        ReviewInputForm(inputForm)
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoadingStatePreview() {
    LoadingState(
        inputForm = BookReviewViewModel.InputForm(rate = 3, message = "hello")
    )
}
