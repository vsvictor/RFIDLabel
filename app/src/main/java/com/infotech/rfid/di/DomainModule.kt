package com.infotech.rfid.di

import com.infotech.rfid.data.local.SettingsSharedPreferences
import com.infotech.mines.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {
    single {
        SettingsSharedPreferences(get())
    }
    single {
        JSTPLoginUseCase(get())
    }
    single {
        JSTPGetUserInfoUseCase(get())
    }
}
