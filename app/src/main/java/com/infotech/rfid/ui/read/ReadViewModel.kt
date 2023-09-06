package com.infotech.rfid.ui.write

import android.app.Application
import com.infotech.rfid.R
import com.infotech.rfid.base.BaseViewModel

class ReadViewModel(override val app: Application) : BaseViewModel<Object>(app) {
    override fun onBackPressed() {
        super.onBackPressed()
        navController?.navigate(R.id.mainFragment)
    }
}