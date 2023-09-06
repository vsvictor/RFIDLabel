package com.infotech.rfid.ui.write

import android.app.Application
import android.nfc.Tag
import android.nfc.tech.MifareUltralight
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infotech.rfid.R
import com.infotech.rfid.base.BaseViewModel
import com.infotech.rfid.data.model.Entity
import com.infotech.rfid.di.provideGSON
import kotlinx.coroutines.launch
import java.nio.charset.Charset
import java.util.UUID

class WriteViewModel(override val app: Application) : BaseViewModel<Object>(app) {
    var onEditData: OnEditData? = null
    override fun onBackPressed() {
        super.onBackPressed()
        navController?.navigate(R.id.mainFragment)
    }
    fun generateID(view: View){
        val uuid = UUID.randomUUID()
        onEditData?.generateID(uuid)
    }
}