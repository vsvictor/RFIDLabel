package com.infotech.rfid

import android.app.Application
import com.infotech.rfid.di.appModule
import com.infotech.rfid.di.domainModule
import com.infotech.rfid.di.mainModule
import com.infotech.rfid.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RFIDApp: Application() {
    private val TAG = RFIDApp::class.java.simpleName
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RFIDApp)
            modules(
                listOf(
                    appModule,
                    repositoryModule,
                    domainModule,
                    mainModule
                )
            )
        }
    }
}