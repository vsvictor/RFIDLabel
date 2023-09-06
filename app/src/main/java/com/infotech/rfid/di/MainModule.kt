package com.infotech.rfid.di

import com.infotech.rfid.MainActViewModel
import com.infotech.rfid.ui.MainFragment
import com.infotech.rfid.ui.MainViewModel
import com.infotech.rfid.ui.login.LoginFragment
import com.infotech.rfid.ui.login.LoginViewModel
import com.infotech.rfid.ui.write.ReadFragment
import com.infotech.rfid.ui.write.ReadViewModel
import com.infotech.rfid.ui.write.WriteFragment
import com.infotech.rfid.ui.write.WriteViewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel {
        MainActViewModel(get())
    }
    fragment {
        LoginFragment()
    }
    viewModel {
        LoginViewModel(get(), get(), get())
    }
    fragment {
        MainFragment()
    }
    viewModel {
        MainViewModel(get())
    }
    fragment {
        WriteFragment()
    }
    viewModel {
        WriteViewModel(get())
    }
    fragment {
        ReadFragment()
    }
    viewModel {
        ReadViewModel(get())
    }
}

