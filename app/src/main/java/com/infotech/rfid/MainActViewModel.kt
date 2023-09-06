package com.infotech.rfid

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import com.infotech.rfid.base.BaseViewModel
import com.infotech.rfid.data.model.*

class MainActViewModel(override val app: Application): BaseViewModel<Any>(app){
    private val TAG = MainActViewModel::class.java.simpleName
    private var _profile: ProfileData? = null
    var profile get() = _profile; set(value) {
        _profile = value
    }

    //var reason: String? = null

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
    }

}