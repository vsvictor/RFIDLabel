package com.infotech.rfid.ui

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.infotech.rfid.R
import com.infotech.rfid.base.BaseFragment
import com.infotech.rfid.base.annotations.Layout
import com.infotech.rfid.databinding.FragmentMainBinding
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
@Layout(R.layout.fragment_main)
class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.model = viewModel
        nfcStateWithPermissionCheck()
    }
    @SuppressLint("NeedOnRequestPermissionsResult")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @NeedsPermission(android.Manifest.permission.NFC)
    fun nfcState(){
        //Toast.makeText(requireContext(), "NFC granted", Toast.LENGTH_SHORT).show()
    }
}