package com.infotech.rfid.data.api.jstp

import com.metarhia.jstp.compiler.annotations.proxy.Call
import com.metarhia.jstp.compiler.annotations.proxy.Proxy
import com.metarhia.jstp.core.Handlers.ManualHandler

@Proxy(interfaceName = "auth")
interface AuthService {
    @Call
    fun login(login: String, password: String, id: String, handler: ManualHandler)

    @Call
    fun logoff(handler: ManualHandler)
}