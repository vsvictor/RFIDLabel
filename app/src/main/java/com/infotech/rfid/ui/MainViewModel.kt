package com.infotech.rfid.ui

import android.app.Application
import android.view.View
import androidx.lifecycle.ViewModel
import com.infotech.rfid.R
import com.infotech.rfid.base.BaseViewModel

class MainViewModel(override val app: Application) : BaseViewModel<Object>(app) {
    override fun onBackPressed() {
    }
    fun onWrite(view: View){
        navController?.navigate(R.id.writeFragment)
    }
    fun onRead(view: View){
        navController?.navigate(R.id.readFragment)
    }
}