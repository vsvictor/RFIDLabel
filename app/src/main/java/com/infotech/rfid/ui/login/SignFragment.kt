package com.infotech.rfid.ui.login

import android.os.Bundle
import com.infotech.rfid.R
import com.infotech.rfid.base.BaseFragment
import com.infotech.rfid.base.annotations.Layout
import com.infotech.rfid.databinding.FragmentSignBinding


@Layout(R.layout.fragment_sign)
class SignFragment : BaseFragment<FragmentSignBinding, LoginViewModel>() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.model = viewModel
    }

}