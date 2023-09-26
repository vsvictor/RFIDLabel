package com.infotech.rfid.ui

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.infotech.rfid.R
import com.infotech.rfid.base.BaseFragment
import com.infotech.rfid.base.annotations.Layout
import com.infotech.rfid.databinding.FragmentMainBinding

@Layout(R.layout.fragment_main)
class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {
    private val TAG = MainFragment::class.java.simpleName
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.model = viewModel
    }
}