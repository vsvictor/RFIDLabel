package com.infotech.rfid.ui.login

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.infotech.artprt.ui.login.dialogs.LoginErrorDialog
import com.infotech.rfid.R

import com.infotech.rfid.base.BaseFragment
import com.infotech.rfid.base.annotations.Layout
import com.infotech.rfid.data.model.ProfileData
import com.infotech.rfid.databinding.FragmentLoginBinding
import com.infotech.rfid.ui.common.OnBottomBarVisible
import com.infotech.rfid.ui.common.OnInternet
import com.infotech.rfid.ui.common.OnNextEnable
import com.infotech.rfid.ui.common.OnProfile
import com.infotech.rfid.ui.login.data.OnHints
import com.infotech.rfid.ui.login.data.OnLogin
import com.infotech.rfid.ui.login.dialogs.NoInternetDialog
import com.infotech.rfid.utils.ShakeDetector

@Layout(R.layout.fragment_login)
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(), OnLogin, OnHints, OnNextEnable, OnInternet {
    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null
    private var shakeDetector: ShakeDetector? = null
    private var onBottomBar: OnBottomBarVisible? = null
    private var onProfile: OnProfile? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.clLogin.isInvisible = false
        binding.clNewPassword.isInvisible = true
        binding.clBottom.isEnabled = false
        binding.llPP.updateLayoutParams<ConstraintLayout.LayoutParams> {
            bottomMargin = onBottomBar?.navigationBarHeight()?:0
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onBottomBar = context as OnBottomBarVisible
        onProfile = context as OnProfile
    }

    override fun onDetach() {
        super.onDetach()
        onBottomBar = null
        onProfile = null
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onBottomBar?.onBottomBarVisible(false)
        viewModel.onLoginState = this
        viewModel.onHints = this
        viewModel.onInternet = this
        viewModel.onNextEnable = this
        binding.model = viewModel
        initSensor()
    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(
            shakeDetector,
            accelerometer,
            SensorManager.SENSOR_DELAY_UI
        )
    }

    override fun onPause() {
        sensorManager!!.unregisterListener(shakeDetector)
        super.onPause()
    }

    override fun onLoggined(profile: ProfileData?) {
        onProfile?.profile = profile
        requireActivity().runOnUiThread {
            navController?.navigate(R.id.mainFragment)
        }
    }

    override fun onNewPassword() {
        binding.clLogin.isVisible = true
        binding.clNewPassword.isVisible = false
    }

    override fun onError() {
        LoginErrorDialog(viewModel, {
            binding.edLogin.background = resources.getDrawable(R.drawable.rect_white_with_red_corner)
            binding.edPassword.background = resources.getDrawable(R.drawable.rect_white_with_red_corner)
        }).show(childFragmentManager, "LOGIN_ERROR")
    }

    override fun onLoginHint(visible: Boolean) {
        binding.tvLoginHint.isInvisible = !visible
    }

    override fun onPasswordHint(visible: Boolean) {
        binding.tvPasswordHint.isInvisible = !visible
    }

    override fun onNextEnable(enable: Boolean) {
        binding.clBottom.isEnabled = enable
    }

    private fun shakeLogin(){
        binding.edLogin.setText(viewModel.mockLogin)
        binding.edPassword.setText(viewModel.mockPassword)
    }
    private fun initSensor() {
        sensorManager = requireActivity().getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        shakeDetector = ShakeDetector()
        shakeDetector!!.setOnShakeListener(object : ShakeDetector.OnShakeListener {
            override fun onShake(count: Int) {
                shakeLogin()
            }
        })
    }

    override fun onNoConnection() {
        NoInternetDialog(viewModel).show(childFragmentManager, "NO_INTERNENT")
    }
}