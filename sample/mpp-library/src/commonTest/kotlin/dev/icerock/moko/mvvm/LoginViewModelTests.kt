/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm

import com.icerockdev.library.sample6.LoginViewModel
import com.icerockdev.library.sample6.UserRepository
import dev.icerock.moko.mvvm.test.TestObserver
import dev.icerock.moko.mvvm.test.TestViewModelScope
import dev.icerock.moko.mvvm.test.createTestEventsDispatcher
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class LoginViewModelTests {
    @BeforeTest
    fun setup() {
        TestViewModelScope.setupViewModelScope(CoroutineScope(Dispatchers.Unconfined))
    }

    @AfterTest
    fun reset() {
        TestViewModelScope.resetViewModelScope()
    }

    @Test
    fun `login successful`() {
        val events = mutableListOf<String>()
        val listener = object : LoginViewModel.EventsListener {
            override fun routeToMainScreen() {
                events.add("routeToMainScreen")
            }

            override fun showError(error: StringDesc) {
                events.add("showError($error)")
            }
        }
        val viewModel = LoginViewModel(
            eventsDispatcher = createTestEventsDispatcher(listener),
            userRepository = object : UserRepository {
                override suspend fun login(email: String, password: String) {
                    assertEquals(expected = "am@icerock.dev", actual = email)
                    assertEquals(expected = "666666", actual = password)
                    events.add("login")
                }
            }
        )
        val loginButtonVisibleObserver = TestObserver<Boolean>()
        viewModel.isLoginButtonVisible.addObserver(loginButtonVisibleObserver)

        assertEquals(expected = false, actual = viewModel.isLoginButtonVisible.value)

        viewModel.email.value = "am@icerock.dev"
        viewModel.password.value = "666666"

        assertEquals(expected = true, actual = viewModel.isLoginButtonVisible.value)

        assertEquals(expected = 1, actual = events.size)
        viewModel.onLoginButtonPressed()
        assertEquals(
            expected = listOf(
                "showError(RawStringDesc(string=inited))",
                "login",
                "routeToMainScreen"
            ),
            actual = events
        )
    }
}
