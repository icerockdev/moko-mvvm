package com.icerockdev

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.icerockdev.databinding.ActivityTestBinding
import com.icerockdev.library.TestViewModel
import dev.icerock.moko.mvvm.MvvmEventsActivity
import dev.icerock.moko.mvvm.createViewModelFactory
import dev.icerock.moko.mvvm.desc.StringDesc
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain

class TestActivity :
    MvvmEventsActivity<ActivityTestBinding, TestViewModel, TestViewModel.EventsListener>(),
    TestViewModel.EventsListener {
    override val layoutId: Int = R.layout.activity_test
    override val viewModelVariableId: Int = BR.viewModel
    override val viewModelClass: Class<TestViewModel> = TestViewModel::class.java

    override fun viewModelFactory(): ViewModelProvider.Factory {
        return createViewModelFactory { TestViewModel(eventsDispatcherOnMain()) }
    }

    override fun showError(error: StringDesc) {
        Toast.makeText(this, error.toString(this), Toast.LENGTH_SHORT).show()
    }
}
