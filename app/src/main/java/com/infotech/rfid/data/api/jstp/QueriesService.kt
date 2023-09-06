package com.infotech.rfid.data.api.jstp

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.metarhia.jstp.compiler.annotations.proxy.Call
import com.metarhia.jstp.compiler.annotations.proxy.Proxy
import com.metarhia.jstp.core.Handlers.ManualHandler
@Proxy(interfaceName = "queries")
interface QueriesService {
    @Call("queries", "execute")
    fun getActions(method: String, categoty: Map<String, String> , argum: HashMap<String, String>, handler: ManualHandler)
    @Call("queries", "execute")
    fun getActionDetail(method: String, categoty: Map<String, String> , argum: Map<String, Any>, handler: ManualHandler)
    @Call("queries", "execute")
    fun declineAction(method: String, categoty: Map<String, String> , argum: Map<String, Any>, handler: ManualHandler)
    @Call("queries", "execute")
    fun getInvolved(method: String, categoty: Map<String, String> , argum: Map<String, Any>, handler: ManualHandler)
    @Call("queries", "execute")
    fun getProgress(method: String, categoty: Map<String, String> , argum: Map<String, Any>, handler: ManualHandler)
    @Call("queries", "execute")
    fun addMessage(method: String, categoty: Map<String, String> , argum: Map<String, Any>, handler: ManualHandler)
    @Call("queries", "execute")
    fun getUserInfo(method: String, categoty: Map<String, String> , argum: Map<String, Any>, handler: ManualHandler)
    @Call("print", "getExportedReport")
    fun getDocs(method: String, categoty: Map<String, String> , argum: Map<String, Any>, handler: ManualHandler)
    @Call("streams", "read")
    fun getStream(arg: String, handler: ManualHandler)
    @Call("streams", "prepareCephFile")
    fun getStreamID(arg: String, handler: ManualHandler)

    @Call("queries", "execute")
    fun getFile(method: String, categoty: Map<String, String> , argum: Map<String, Any>, handler: ManualHandler)
}