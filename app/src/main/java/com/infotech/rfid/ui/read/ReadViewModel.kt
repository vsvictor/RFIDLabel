package com.infotech.rfid.ui.write

import android.app.Application
import android.util.Log
import com.infotech.rfid.R
import com.infotech.rfid.base.BaseViewModel

class ReadViewModel(override val app: Application) : BaseViewModel<Object>(app) {
    private val TAG = ReadViewModel::class.java.simpleName
}