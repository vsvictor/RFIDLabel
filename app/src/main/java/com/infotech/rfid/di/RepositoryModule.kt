package com.infotech.rfid.di

import com.infotech.rfid.data.repository.*
import org.koin.dsl.module

val repositoryModule = module {
    single {
        LocalDataSourceImpl(get())
    }
    single<LocalDataSource> {
        LocalDataSourceImpl(get())
    }
    single {
        RemoteDataSourceImpl(get(), get())
    }
    single<RemoteDataSource> {
        RemoteDataSourceImpl(get(), get())
    }
    single<Repository> {
        RepositoryImpl(get(), get())
    }
}