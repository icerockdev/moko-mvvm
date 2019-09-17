/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.app.sample5

import androidx.lifecycle.ViewModelProvider
import com.icerockdev.app.BR
import com.icerockdev.app.R
import com.icerockdev.app.databinding.ActivityValidationAllBinding
import com.icerockdev.library.sample5.ValidationAllViewModel
import dev.icerock.moko.mvvm.MvvmActivity
import dev.icerock.moko.mvvm.createViewModelFactory

class ValidationAllActivity : MvvmActivity<ActivityValidationAllBinding, ValidationAllViewModel>() {
    override val layoutId: Int = R.layout.activity_validation_all
    override val viewModelVariableId: Int = BR.viewModel
    override val viewModelClass: Class<ValidationAllViewModel> =
        ValidationAllViewModel::class.java

    override fun viewModelFactory(): ViewModelProvider.Factory {
        return createViewModelFactory { ValidationAllViewModel() }
    }
}
