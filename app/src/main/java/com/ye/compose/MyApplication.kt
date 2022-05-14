package com.ye.compose

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import com.ye.compose.model.sight
import com.ye.compose.model.sightItem
import com.ye.compose.repository.ApiService
import com.ye.compose.repository.LocationListener
import com.ye.compose.ui.AppViewModel
import com.ye.compose.utils.LocalJsonResolutionUtils
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.withTimeout
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication:Application(){
    val TAG:String=MyApplication::class.java.name
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        @SuppressLint("StaticFieldLeak")
        lateinit var apiService: ApiService
        const val BASE_URL="http://192.168.1.16"
        //"http://123.57.21.210/"
        //"http://192.168.31.104:8080/"
        lateinit var appViewModel:AppViewModel
        lateinit var sightList:MutableList<sightItem>
    }
    override fun onCreate() {
        super.onCreate()
        context=applicationContext
        appViewModel= AppViewModel()
        sightList=ArrayList()
        val index= listOf(1,2,3,4,5,6)
        for(i in index){
            val slight_string: String = LocalJsonResolutionUtils.getJson(this, "sight$i.json")
            val list = LocalJsonResolutionUtils.JsonToObject(slight_string, sight::class.java)
            for (item in list){
                    sightList.add(item)
                }
        }
        apiService=Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}