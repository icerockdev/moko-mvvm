/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.viewbinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import dev.icerock.moko.mvvm.viewmodel.ViewModel

abstract class MvvmActivity<VB : ViewBinding, VM : ViewModel> : AppCompatActivity() {
    protected lateinit var binding: VB
    protected lateinit var viewModel: VM

    protected abstract val viewModelClass: Class<VM>

    protected abstract fun viewModelFactory(): ViewModelProvider.Factory

    protected abstract fun viewBindingInflate(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val provider = ViewModelProvider(this, viewModelFactory())
        viewModel = provider.get(viewModelClass)

        binding = viewBindingInflate()

        setContentView(binding.root)
    }
}
