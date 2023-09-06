package com.infotech.rfid.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import com.infotech.rfid.R
import com.infotech.rfid.base.BaseFragment
import com.infotech.rfid.base.annotations.Layout
import com.infotech.rfid.databinding.FragmentLPBinding

@Layout(R.layout.fragment_l_p)
class LPFragment : BaseFragment<FragmentLPBinding, LoginViewModel>() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.model = viewModel
    }

}