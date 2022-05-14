package com.ye.compose.ui


import AndroidViewBindingPage
import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ye.compose.model.PlanningData
import com.ye.compose.model.Slight
import com.ye.compose.model.SlightVo
import com.ye.compose.model.User
import com.ye.compose.repository.DataRepository
import com.ye.compose.repository.Status
import com.ye.compose.utils.MD5Utils.stringToMD5
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.ye.compose.MyApplication.Companion.context
import com.ye.compose.repository.LocationListener




class AppViewModel:ViewModel() {
     private val TAG:String="AppViewModel"
     private val user:MutableLiveData<User> by lazy { MutableLiveData<User>()}
     private val state:MutableLiveData<Status> by lazy { MutableLiveData<Status>()}
     private val mustSlights:MutableLiveData<MutableList<Slight>> by lazy{MutableLiveData<MutableList<Slight>>()}
     private val noSlights:MutableLiveData<MutableList<Slight>> by lazy{MutableLiveData<MutableList<Slight>>()}
     private val resSlights:MutableLiveData<List<SlightVo>> by lazy { MutableLiveData<List<SlightVo>>() }
     private val lat:MutableLiveData<Double> by lazy { MutableLiveData<Double>() }
     private val lng:MutableLiveData<Double> by lazy { MutableLiveData<Double>() }
     fun getUser():LiveData<User>{
         if(state.value?.status==0){
             viewModelScope.launch {
                 user.value= user.value?.let { DataRepository.getUser(it.phone_number) }
             }
         }
         else initUser()
         return user
     }
    fun getLat():LiveData<Double>{
        return lat
    }
    fun getLng():LiveData<Double>{
        return lng;
    }
    fun getState():LiveData<Status>{
        return state
    }
    fun initUser(){
        user.value= User(phone_number = "",password = "",name = "",tags = arrayListOf())
    }
    fun clearStatus(){
        state.value=null
    }
     @SuppressLint("SimpleDateFormat")
     fun addUser(phone_number: String, password:String):LiveData<Status>{
        val name=stringToMD5(phone_number).substring(0,phone_number.length)
        val passwordMd5= stringToMD5(password).substring(0,password.length)
        val nowTime=System.currentTimeMillis()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val nowTimeStr= sdf.format(nowTime)
        user.value=User(phone_number = phone_number,password = passwordMd5,name = "用户_$name",register_date = nowTimeStr,tags = arrayListOf())
//         user.postValue(User(phone_number = phone_number,password = passwordMd5,name = "YCY_$name",register_date = nowTimeStr))
        viewModelScope.launch{
//            state.postValue(DataRepository.addUser(user.value!!))
            state.value=DataRepository.addUser(user.value!!)
        }
        return state
    }
     fun login(phone_number: String,password:String):LiveData<Status>{
        val passwordMd5= stringToMD5(password).substring(0,password.length)
        user.value= User(phone_number = phone_number,password = passwordMd5,tags = arrayListOf())
        viewModelScope.launch {
//            state.postValue( DataRepository.login(user.value!!))
            state.value=DataRepository.login(user.value!!)
        }
        return state
    }
    fun getLatAndLng(){
        val myLocationListener= LocationListener()
        val mLocationClient = LocationClient(context)
        mLocationClient.registerLocationListener(myLocationListener)
        val option=LocationClientOption()
        option.locationMode=LocationClientOption.LocationMode.Hight_Accuracy
        option.coorType="bd09ll"
        mLocationClient.locOption=option
        mLocationClient.start()
        myLocationListener.setListenGps(LocationListener.GetGps { bdLocation ->
            lat.value=bdLocation.latitude
            lng.value=bdLocation.longitude
            Log.d(TAG, "getLatAndLng: "+lat.value+"\n"+lng.value)
        })
    }
    fun planning(
        dayNum:Int,
        mustSlight: MutableList<Slight>,
        noSlight: MutableList<Slight>,
        city:String,
    ){
        val nowTime=System.currentTimeMillis()
        val planningData= lat.value?.let { lng.value?.let { it1 -> PlanningData(dayNum = dayNum,mustSlight=mustSlight,noSlight = noSlight,city = city,time = nowTime,tags = user.value!!.tags,lat = it,lng = it1) } }
        viewModelScope.launch {
            val response= planningData?.let { DataRepository.getPlan(it) }
            if(response is List<*>)
            {
                resSlights.value=response as List<SlightVo>
            }
        }
    }
}
