/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.sample.declarativeui.android

import androidx.compose.runtime.Composable
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun ComposeApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(
                        "books",
                        navOptions = NavOptions.Builder().setLaunchSingleTop(true).build()
                    )
                }
            )
        }
        composable("books") {
            BookListScreen(
                onOpenBook = {
                    navController.navigate("books/$it/review")
                }
            )
        }
        composable("books/{bookId}/review") { entry ->
            val bookId: Int = entry.arguments?.getInt("bookId").let { requireNotNull(it) }
            BookReviewScreen(
                bookId = bookId,
                onCloseScreen = { navController.popBackStack() }
            )
        }
    }
}
