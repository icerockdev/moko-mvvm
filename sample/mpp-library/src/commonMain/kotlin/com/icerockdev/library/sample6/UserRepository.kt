/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.library.sample6

interface UserRepository {
    suspend fun login(email: String, password: String)
}