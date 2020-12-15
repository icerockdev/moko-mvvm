package com.icerockdev.app.sample7

import androidx.lifecycle.ViewModelProvider
import com.icerockdev.app.BR
import com.icerockdev.app.R
import com.icerockdev.app.databinding.FragmentSimpleBinding
import com.icerockdev.library.sample1.SimpleViewModel
import dev.icerock.moko.mvvm.MvvmFragment
import dev.icerock.moko.mvvm.createViewModelFactory

class SimpleFragment : MvvmFragment<FragmentSimpleBinding, SimpleViewModel>() {

    override val layoutId: Int = R.layout.fragment_simple
    override val viewModelVariableId: Int = BR.viewModel
    override val viewModelClass: Class<SimpleViewModel> = SimpleViewModel::class.java

    override fun viewModelFactory(): ViewModelProvider.Factory {
        return createViewModelFactory { SimpleViewModel() }
    }

}