package com.infotech.rfid

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.MifareUltralight
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.KeyEvent
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.infotech.rfid.base.BaseActivity
import com.infotech.rfid.base.BaseFragment
import com.infotech.rfid.base.annotations.Layout
import com.infotech.rfid.base.interfaces.OnLanguageChanged
import com.infotech.rfid.data.local.SettingsSharedPreferences
import com.infotech.rfid.data.model.*
import com.infotech.rfid.databinding.ActivityMainBinding
import com.infotech.rfid.di.provideGSON
import com.infotech.rfid.ui.OnEntity
import com.infotech.rfid.ui.common.*
import com.infotech.rfid.ui.login.LoginViewModel
import com.infotech.rfid.utils.*
import org.koin.java.KoinJavaComponent
import java.nio.charset.Charset

//@RuntimePermissions
@Layout(R.layout.activity_main)
class MainActivity : BaseActivity<ActivityMainBinding, MainActViewModel>(), OnBottomBarVisible, OnLanguageChanged, OnProfile, OnClear, OnEntity{
    private val TAG = MainActivity::class.java.simpleName
    private var actionBarHeight = 0
    private var bottomHeight = 0
    private var currentID = 0

    protected var nfcAdapter: NfcAdapter? = null
    private var pendingIntent: PendingIntent? = null

    private var isReady = true
    private var ent: Entity? = null
    private var strToSave: String?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transparentStatusBar()
        val tv = TypedValue()
        if (this.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(
                tv.data,
                resources.displayMetrics
            )
        }
        navController = findNavController(R.id.nav_host_fragment)
        binding.navView.setupWithNavController(navController)
        binding.navView.setOnItemSelectedListener {
            if(it.itemId != currentID) {
                currentID = it.itemId
                navController.navigate(currentID)
            }
            true
        }
        bottomHeight = binding.navView.layoutParams.height
        val sh by KoinJavaComponent.inject(SettingsSharedPreferences::class.java)
        val startFragment = sh.prefs.getInt("start_from", -1)
        if(startFragment > 0){
            sh.prefs.edit().remove("start_from").commit()
            navController.navigate(startFragment)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(it.action)){
                    (getCurrentFragment() as BaseFragment<*, *>).onNewIntent(it)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }
    override fun onStart() {
        super.onStart()
        //onInitNFCWithPermissionCheck()
    }
    override fun onResume() {
        super.onResume()
        //onResumeNFCWithPermissionCheck()
    }
    override fun onPause() {
        super.onPause()
        //onStopNFCWithPermissionCheck()
    }
/*
    @SuppressLint("NeedOnRequestPermissionsResult")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }
*/
    //@NeedsPermission(android.Manifest.permission.NFC)
    fun onInitNFC(){
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        val inte = Intent(this, javaClass)
        inte.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        pendingIntent = PendingIntent.getActivity(this, 0,inte , PendingIntent.FLAG_MUTABLE)
    }
    //@NeedsPermission(android.Manifest.permission.NFC)
    fun onResumeNFC(){
        nfcAdapter?.let {
            it.enableForegroundDispatch(this, pendingIntent, null, null)
        }
    }
    //@NeedsPermission(android.Manifest.permission.NFC)
    fun onStopNFC(){
        nfcAdapter?.let {
            it.disableForegroundDispatch(this)
        }
    }


    override fun getNavHostFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
    }
    override fun onBottomBarVisible(visible: Boolean) {
        try {
            binding.navView.post {
                binding.navView.isVisible = visible
            }
        }catch (ex: UninitializedPropertyAccessException){
            ex.printStackTrace()
        }
    }
    override fun navigationBarHeight(): Int {
        return navigationBarHeight
    }

    override fun bottomBarHeight(): Int {
        return bottomHeight
    }

    override var profile: ProfileData?
        get() = viewModel.profile
        set(value) { viewModel.profile = value }

    override fun onClearIntent() {
        isReady = true
    }

    override var entity: Entity?
        get() = ent
        set(value) {
            ent = value
            val gson = provideGSON()
            strToSave = gson.toJson(entity)
        }
}