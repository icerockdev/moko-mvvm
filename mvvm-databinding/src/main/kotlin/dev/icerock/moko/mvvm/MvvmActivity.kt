/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import dev.icerock.moko.mvvm.viewmodel.ViewModel

abstract class MvvmActivity<DB : ViewDataBinding, VM : ViewModel> : AppCompatActivity() {
    protected lateinit var binding: DB
    protected lateinit var viewModel: VM

    protected abstract val layoutId: Int
    protected abstract val viewModelVariableId: Int
    protected abstract val viewModelClass: Class<VM>

    protected abstract fun viewModelFactory(): ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val provider = ViewModelProvider(this, viewModelFactory())
        viewModel = provider.get(viewModelClass)

        binding = DataBindingUtil.setContentView(this, layoutId)

        binding.lifecycleOwner = this

        binding.setVariable(viewModelVariableId, viewModel)
    }
}
