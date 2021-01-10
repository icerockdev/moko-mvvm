/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.library.sample6

import kotlinx.coroutines.delay

class MockUserRepository : UserRepository {
    override suspend fun login(email: String, password: String) {
        delay(WAIT_TIME_MS)
    }

    private companion object {
        const val WAIT_TIME_MS = 2000L
    }
}
