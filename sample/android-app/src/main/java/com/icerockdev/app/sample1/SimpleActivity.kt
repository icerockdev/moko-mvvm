/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.app.sample1

import androidx.lifecycle.ViewModelProvider
import com.icerockdev.app.BR
import com.icerockdev.app.R
import com.icerockdev.app.databinding.ActivitySimpleBinding
import com.icerockdev.library.sample1.SimpleViewModel
import dev.icerock.moko.mvvm.MvvmActivity
import dev.icerock.moko.mvvm.createViewModelFactory

class SimpleActivity : MvvmActivity<ActivitySimpleBinding, SimpleViewModel>() {
    override val layoutId: Int = R.layout.activity_simple
    override val viewModelVariableId: Int = BR.viewModel
    override val viewModelClass: Class<SimpleViewModel> = SimpleViewModel::class.java

    override fun viewModelFactory(): ViewModelProvider.Factory {
        return createViewModelFactory { SimpleViewModel() }
    }
}
