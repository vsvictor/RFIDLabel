package com.infotech.rfid.data.api.rest

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.infotech.rfid.base.errors.ErrorBody
import com.infotech.rfid.base.errors.ExtException
import okhttp3.ResponseBody
import org.koin.java.KoinJavaComponent.inject
import retrofit2.Response


class RestApiHelperImpl(val service: RestApiInterface, val configInterface: ConfigInterface) : RestApiHelper {
    private val TAG = RestApiHelperImpl::class.java.simpleName
    private val gson by inject(Gson::class.java)

    //override suspend fun getConfig(): MapData? = configInterface.getConfig().run{try{handleError(this)}catch (ex: Throwable){throw ex}}

    private fun <T> handleError(res: Response<T>):T{
        val code = res.code()
        if(code >= 200 && code < 300) {
            return res.body()!!
        }
        else{
            throw parse(code, res.errorBody())
        }
    }
    private fun parse(code: Int, response: ResponseBody?): ExtException {
        response?.let {
            val docsType = object : TypeToken<ErrorBody>() {}.type
            val body  = gson.fromJson<ErrorBody>(response.string(), docsType)
            return ExtException(code, body)
        }?: kotlin.run {
            return ExtException(-1, ErrorBody("Undefined error"))
        }
    }
}