package com.infotech.rfid.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.infotech.rfid.BuildConfig
import com.infotech.rfid.base.errors.DataError
import com.infotech.rfid.base.errors.ErrorAdapter
import com.infotech.rfid.data.api.jstp.JSTPApiHelper
import com.infotech.rfid.data.api.jstp.JSTPApiHelperImpl
import com.infotech.rfid.data.api.rest.ConfigInterface
import com.infotech.rfid.data.api.rest.RestApiHelper
import com.infotech.rfid.data.api.rest.RestApiHelperImpl
import com.infotech.rfid.data.api.rest.RestApiInterface
import com.infotech.rfid.data.local.SettingsSharedPreferences
import com.infotech.rfid.data.model.*
import com.infotech.rfid.data.model.adapters.*
import com.infotech.rfid.domain.interceptors.CustomHttpLoggingInterceptor
import com.infotech.rfid.utils.NetworkHelper
import com.metarhia.jstp.connection.Connection
import com.metarhia.jstp.transport.TCPTransport
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val JSTP_APP_NAME = "bravo"

val appModule = module {
    single { provideOkHttpClient(get(), get()) }
    single { provideGSON() }
    single { provideApiService(provideRetrofit(get(), get(), BuildConfig.REST_URL)) }
    single { provideConfigService(provideRetrofit(get(), get(), BuildConfig.CONFIG_BASE_URL)) }
    single { provideNetworkHelper(androidContext()) }
    single { getSharedPreferences(androidContext()) }
    single<RestApiHelper> { return@single RestApiHelperImpl(get(), get()) }
    single { provideJSTP(get(), get(), BuildConfig.HOST, BuildConfig.PORT)}
}

private fun getSharedPreferences(context: Context) = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

private fun provideNetworkHelper(context: Context) = NetworkHelper(context)


private fun provideOkHttpClient(sh: SettingsSharedPreferences, context: Context): OkHttpClient {
    if (BuildConfig.DEBUG) {
        val loggingInterceptor = CustomHttpLoggingInterceptor()
        loggingInterceptor.apply { loggingInterceptor.level = CustomHttpLoggingInterceptor.Level.BODY }
        return OkHttpClient.Builder()
            .cache(null)
            .readTimeout(300, TimeUnit.SECONDS)
            .writeTimeout(300, TimeUnit.SECONDS)
            .connectTimeout(300, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    } else return OkHttpClient
        .Builder()
        .cache(null)
        .readTimeout(300, TimeUnit.SECONDS)
        .writeTimeout(300, TimeUnit.SECONDS)
        .connectTimeout(300, TimeUnit.SECONDS)
        .build()
}

fun provideGSON(): Gson {
    return GsonBuilder()
        .registerTypeHierarchyAdapter(ProfileData::class.java, ProfileAdapter())
        .registerTypeHierarchyAdapter(UserInfoData::class.java, UserInfoAdapter())
        .registerTypeHierarchyAdapter(DataError::class.java, ErrorAdapter())
        .registerTypeHierarchyAdapter(Entity::class.java, EntityAdapter())
        .setLenient()
        .create()
}

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    gson: Gson,
    BASE_URL: String
): Retrofit =
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

private fun provideApiService(retrofit: Retrofit): RestApiInterface = retrofit.create(RestApiInterface::class.java)
private fun provideConfigService(retrofit: Retrofit): ConfigInterface = retrofit.create(ConfigInterface::class.java)
private fun provideJSTP(context: Context, gson: Gson, host: String, port: Int): JSTPApiHelper {
    val transport = TCPTransport(host, port, BuildConfig.USE_TLS)
    return JSTPApiHelperImpl(context, gson, Connection(transport))
}

