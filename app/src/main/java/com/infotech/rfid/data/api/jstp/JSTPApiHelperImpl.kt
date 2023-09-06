package com.infotech.rfid.data.api.jstp

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.infotech.rfid.R
import com.infotech.rfid.base.errors.ErrorBody
import com.infotech.rfid.base.errors.ErrorData
import com.infotech.rfid.data.model.ProfileData
import com.infotech.rfid.data.model.UserInfoData
import com.infotech.rfid.di.JSTP_APP_NAME
import com.metarhia.jstp.connection.Connection
import com.metarhia.jstp.connection.ConnectionListener
import com.metarhia.jstp.core.JSInterfaces.JSObject
import kotlin.collections.HashMap

const val ACTION_NEW_EVENT = "action_new_event"
const val ACTION_NEW_MESSAGE = "action_new_message"
const val ACTION_NEW_HEAD = "action_new_head"
const val ACTION_NEW_VEHICLE_STATE = "action_new_vehicle_state"
const val ACTION_NEW_STATUS = "action_new_status"
class JSTPApiHelperImpl(val context: Context, val gson: Gson, val connection: Connection): JSTPApiHelper {
    private val JSTP_NAME = "OperDispatcherManagement"
    private val TAG = JSTPApiHelperImpl::class.java.simpleName
    val auth = JSTPAuthService(connection)
    val queries = JSTPQueriesService(connection)

    fun checkConnection(connected: () -> Unit, closed: () -> Unit){
        if(connection.isConnected){
            connected.invoke()
        }else{
            val listener = object: ConnectionListener{
                override fun onConnected(conn: Boolean) {
                    Log.d(TAG, "Connected")
                    connection.removeListener(this)
                    connected.invoke()
                }
                override fun onMessageRejected(obj: JSObject<*>?) {
                    Log.d(TAG, "Rejected")
                    connection.removeListener(this)
                }
                override fun onConnectionClosed() {
                    Log.d(TAG, "Closed")
                    connection.removeListener(this)
                    closed.invoke()
                }
            }
            connection.addListener(listener)
            connection.connect(JSTP_APP_NAME)
        }
    }
    override suspend fun login(sLogin: String, sPassword: String, success: (ProfileData) -> Unit, fail: (ErrorData) -> Unit): Nothing? {
        checkConnection(
            {
                Log.d(TAG, "Connected")
                auth.login(sLogin, sPassword, JSTP_NAME, ProfileHandler(gson, success = {
                    success.invoke(it)
                }, fail = {
                    fail.invoke(it)
                }))
            },
            {
                Log.d(TAG, "Not connected")

                fail.invoke(ErrorData(-1, ErrorBody(context.getString(R.string.connection_error))))
            })
        return null
    }

    override suspend fun getUserInfo(success: (UserInfoData) -> Unit, fail: (ErrorData) -> Unit): Nothing? {
        checkConnection({
            val js = HashMap<String, String>().apply {
                put("Category", "CrewActions")
            }
            queries.getUserInfo("GetUserInfo", js, HashMap<String, String>(), UserInfoHandler(gson,
                {
                    success.invoke(it)
                },
                {
                    fail.invoke(it)
                }))
        }, {
            Log.d(TAG, "Not connected")
            fail.invoke(ErrorData(-1, ErrorBody(context.getString(R.string.connection_error))))
        })
        return null
    }

}