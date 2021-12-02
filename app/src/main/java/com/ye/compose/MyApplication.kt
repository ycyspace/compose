package com.ye.compose

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.ye.compose.repository.ApiService
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.withTimeout
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication:Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        @SuppressLint("StaticFieldLeak")
        lateinit var apiService: ApiService
        const val BASE_URL="http://192.168.31.104"
        //"http://123.57.21.210/"
        //"http://192.168.31.104:8080/"
    }
    override fun onCreate() {
        super.onCreate()
        context=applicationContext
        apiService=Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}