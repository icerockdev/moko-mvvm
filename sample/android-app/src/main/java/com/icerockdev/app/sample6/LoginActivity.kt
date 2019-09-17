/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.app.sample6

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.icerockdev.app.BR
import com.icerockdev.app.R
import com.icerockdev.app.databinding.ActivityLoginBinding
import com.icerockdev.library.sample6.LoginViewModel
import com.icerockdev.library.sample6.MockUserRepository
import dev.icerock.moko.mvvm.MvvmEventsActivity
import dev.icerock.moko.mvvm.createViewModelFactory
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain
import dev.icerock.moko.resources.desc.StringDesc

class LoginActivity :
    MvvmEventsActivity<ActivityLoginBinding, LoginViewModel, LoginViewModel.EventsListener>(),
    LoginViewModel.EventsListener {

    override val layoutId: Int = R.layout.activity_login
    override val viewModelVariableId: Int = BR.viewModel
    override val viewModelClass: Class<LoginViewModel> =
        LoginViewModel::class.java

    override fun viewModelFactory(): ViewModelProvider.Factory {
        return createViewModelFactory {
            LoginViewModel(
                userRepository = MockUserRepository(),
                eventsDispatcher = eventsDispatcherOnMain()
            )
        }
    }

    override fun routeToMainScreen() {
        Toast.makeText(this, "route to main page here", Toast.LENGTH_SHORT).show()
    }

    override fun showError(error: StringDesc) {
        Toast.makeText(this, error.toString(context = this), Toast.LENGTH_SHORT).show()
    }
}
