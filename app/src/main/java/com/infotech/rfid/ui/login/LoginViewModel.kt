package com.infotech.rfid.ui.login

import android.app.Application
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.lifecycle.viewModelScope
import com.infotech.mines.domain.usecase.JSTPGetUserInfoUseCase
import com.infotech.mines.domain.usecase.JSTPLoginUseCase
import com.infotech.rfid.BuildConfig
import com.infotech.rfid.R
import com.infotech.rfid.base.BaseViewModel
import com.infotech.rfid.base.errors.ErrorBody
import com.infotech.rfid.base.errors.ErrorData
import com.infotech.rfid.data.local.SettingsSharedPreferences
import com.infotech.rfid.data.model.ProfileData
import com.infotech.rfid.data.model.UserInfoData
import com.infotech.rfid.ui.common.OnInternet
import com.infotech.rfid.ui.common.OnNextEnable
import com.infotech.rfid.ui.common.OnUnderstand
import com.infotech.rfid.ui.login.data.OnHints
import com.infotech.rfid.ui.login.data.OnLogin
import com.infotech.rfid.utils.NetworkHelper
import org.koin.java.KoinJavaComponent

class LoginViewModel(override val app: Application,
                     private val loginUseCase: JSTPLoginUseCase,
                     private val getUserInfoUseCase: JSTPGetUserInfoUseCase) : BaseViewModel<Object>(app),
    OnUnderstand {
    private val TAG = LoginViewModel::class.java.simpleName
    var mockPassword = BuildConfig.MOCK_PASSWORD
    var mockLogin = BuildConfig.MOCK_LOGIN
    private var _login: String? = null
    var login get() = _login; set(value) {
        _login= value
        onHints?.onLoginHint(!TextUtils.isEmpty(_login))
        val isLognValid =  !TextUtils.isEmpty(_login)
        val isPasswordValid = true //_password?.isValidPassword()?:false && _password?.length?:0 >= 10
        onNextEnable?.onNextEnable(isLognValid && isPasswordValid)
    }
    var _password: String? = null
    var password get() = _password; set(value) {
        _password = value
        onHints?.onPasswordHint(!TextUtils.isEmpty(_password))
        val isLognValid =  !TextUtils.isEmpty(_login)
        val isPasswordValid = true //_password?.isValidPassword()?:false && _password?.length?:0 >= 10
        onNextEnable?.onNextEnable(isLognValid && isPasswordValid)
    }
    private var newPassword: String? = null
    var onLoginState: OnLogin? = null
    var onHints: OnHints? = null
    var onNextEnable: OnNextEnable? = null
    var onInternet: OnInternet? = null
    fun onLoginChanged(editable: Editable){
        login = editable.toString()
    }
    fun onPasswordChanged(editable: Editable){
        password = editable.toString()
    }
    fun onLogin(view: View){
        onLoginState?.onLoggined(null)
    }
    fun onSignin(onLogin:(ProfileData)->Unit, onError:((ErrorData)->Unit)? = null){
        login?.let { l ->
            password?.let { p ->
                loginUseCase.load(viewModelScope, JSTPLoginUseCase.Params(l, p, newPassword),
                    { prof ->
                        Log.d(TAG, "Login")
                        getUserInfo {
                            //sh.prefs.edit().putString("login", login).putString("password", password).commit()
                            prof.crewID = it.crewID
                            prof.canBeHead = it.canBeHead
                            prof.userID = it.userID
                            onLogin.invoke(prof)
                        }

                    },
                    {
                        //Log.d
                        if(NetworkHelper(app).isNetworkConnected()){
                            //error.postValue(it)
                            onLoginState?.onError()
                        }else{
                            //val body = ErrorBody(app.getString(R.string.connection_error))
                            //val err = ErrorData(-1, body)
                            //error.postValue(err)
                            onInternet?.onNoConnection()
                        }
                    })
            }
        }
    }

    fun getUserInfo(callback: (UserInfoData)-> Unit){
        getUserInfoUseCase.load(viewModelScope, Unit,
            success = {
                Log.d(TAG, it.toString())
                callback.invoke(it)
            },
            fail = {
                error.postValue(it)
            })
    }

    override fun onUnderstand() {
    }
}