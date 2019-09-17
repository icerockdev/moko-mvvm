/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.app.sample4

import androidx.lifecycle.ViewModelProvider
import com.icerockdev.app.BR
import com.icerockdev.app.R
import com.icerockdev.app.databinding.ActivityValidationMergeBinding
import com.icerockdev.library.sample4.ValidationMergeViewModel
import dev.icerock.moko.mvvm.MvvmActivity
import dev.icerock.moko.mvvm.createViewModelFactory

class ValidationMergeActivity :
    MvvmActivity<ActivityValidationMergeBinding, ValidationMergeViewModel>() {
    override val layoutId: Int = R.layout.activity_validation_merge
    override val viewModelVariableId: Int = BR.viewModel
    override val viewModelClass: Class<ValidationMergeViewModel> =
        ValidationMergeViewModel::class.java

    override fun viewModelFactory(): ViewModelProvider.Factory {
        return createViewModelFactory { ValidationMergeViewModel() }
    }
}
