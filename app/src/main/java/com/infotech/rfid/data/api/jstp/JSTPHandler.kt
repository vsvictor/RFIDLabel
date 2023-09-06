package com.infotech.rfid.data.api.jstp

import android.util.Log
import com.google.gson.Gson
import com.infotech.rfid.base.annotations.DataType
import com.infotech.rfid.base.errors.DataError
import com.infotech.rfid.base.errors.ErrorData
import com.metarhia.jstp.core.Handlers.ManualHandler
import com.metarhia.jstp.core.JSInterfaces.JSObject


open class JSTPHandler<M>(open val gson: Gson, val success: (M)->Unit, val fail: (ErrorData)->Unit):     ManualHandler {
    private val TAG = JSTPHandler::class.java.simpleName
    override fun onMessage(obj: JSObject<*>?) {
        try {
            Log.d(TAG, "Object:" + obj.toString())
            //val vmClass = javaClass.getAnnotation(DataType::class.java).type.java
            val vmClass = javaClass.getAnnotation(DataType::class.java).type.java
            Log.d(TAG, "Class:" + vmClass)
            val js = gson.toJson(obj)
            Log.d(TAG, "JSON:" + js.toString())
            val data = gson.fromJson(js, vmClass)
            data?.let {
                success.invoke(it as M)
            } ?: kotlin.run {
                val data = gson.fromJson(js, DataError::class.java)
                fail.invoke(ErrorData(data.error))
            }
        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }

    override fun onError(err: Int) {
        Log.d(TAG, "Error:"+err)
        fail.invoke(ErrorData(err))
    }
}
