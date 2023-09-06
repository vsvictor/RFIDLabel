package com.infotech.rfid.domain

import com.infotech.rfid.base.errors.ErrorBody
import com.infotech.rfid.base.errors.ErrorData
import com.infotech.rfid.base.errors.ExtException
import com.infotech.rfid.base.interfaces.OnLoader
import kotlinx.coroutines.*

abstract class BaseUseCase<T, P> : AbstractUseCase() {

    protected open suspend fun onLaunch(params: P): T? = null
    protected open suspend fun onLoad(params: P, success: (T) -> Unit, fail:(ErrorData) -> Unit) = null
    protected open suspend fun onSend(params: P) = null
    protected var loader: OnLoader? = null

    fun setOnLoaderListener(listener: OnLoader?){
        this.loader = listener
    }

    open fun execute(coroutineScope: CoroutineScope, params: P, success: (T) -> Unit, fail:(ErrorData) -> Unit) {
        loader?.show(true)
        scope = coroutineScope
        job = coroutineScope.launch(launchDispatcher) {
            try {
                val res= onLaunch(params)
                res?.let {
                    withContext(Dispatchers.Main) {
                        loader?.show(false)
                        success.invoke(it)
                    }
                }?: kotlin.run {
                    loader?.show(false)
                    throw ExtException(-1, ErrorBody("Undefined error"))
                }
            }catch (ex: Throwable){
                var err = ErrorData()
                if(ex is ExtException) err = ErrorData(ex.code, ex.err)
                else ex.message?.let {
                    err = ErrorData(-1, ErrorBody(it))
                }
                withContext(Dispatchers.Main) {
                    loader?.show(false)
                    fail.invoke(err)
                }
            }
        }
    }
    fun load(coroutineScope: CoroutineScope, param: P, success: (T) -> Unit, fail: (ErrorData) -> Unit) {
        scope = coroutineScope
        job = coroutineScope.launch {
            onLoad(param, success, fail)
        }
    }
    fun send(coroutineScope: CoroutineScope, param: P){
        scope = coroutineScope
        job = coroutineScope.launch {
            onSend(param)
        }
    }
}