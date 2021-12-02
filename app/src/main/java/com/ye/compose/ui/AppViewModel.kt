package com.ye.compose.ui


import android.annotation.SuppressLint
import android.text.Html
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ye.compose.model.Slight
import com.ye.compose.model.User
import com.ye.compose.repository.DataRepository
import com.ye.compose.repository.Status
import com.ye.compose.utils.MD5Utils.stringToMD5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

class AppViewModel:ViewModel() {
     private val user:MutableLiveData<User> by lazy { MutableLiveData<User>()}
     private val state:MutableLiveData<Status> by lazy { MutableLiveData<Status>()}
     private val mustSlights:MutableLiveData<MutableList<Slight>> by lazy{MutableLiveData<MutableList<Slight>>()}
     private val noSlights:MutableLiveData<MutableList<Slight>> by lazy{MutableLiveData<MutableList<Slight>>()}
     fun getUser():LiveData<User>{
         if(state?.value?.status==0){
             viewModelScope.launch {
                 user.value= user.value?.let { DataRepository.getUser(it.phone_number) } as User?
             }
         }
         else{user.value= User(phone_number = "",password = "",name = "")}
         return user
     }
    fun getState():LiveData<Status>{
        return state
    }
     @SuppressLint("SimpleDateFormat")
     fun addUser(phone_number: String, password:String):LiveData<Status>{
        val name=stringToMD5(phone_number).substring(0,phone_number.length)
        val passwordMd5= stringToMD5(password).substring(0,password.length)
        val nowTime=System.currentTimeMillis()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val nowTimeStr= sdf.format(nowTime)
        user.value=User(phone_number = phone_number,password = passwordMd5,name = "YCY_$name",register_date = nowTimeStr)
//         user.postValue(User(phone_number = phone_number,password = passwordMd5,name = "YCY_$name",register_date = nowTimeStr))
        viewModelScope.launch{
//            state.postValue(DataRepository.addUser(user.value!!))
            state.value=DataRepository.addUser(user.value!!)
        }
        return state
    }
     fun login(phone_number: String,password:String):LiveData<Status>{
        val passwordMd5= stringToMD5(password).substring(0,password.length)
        user.value= User(phone_number = phone_number,password = passwordMd5)
        viewModelScope.launch {
//            state.postValue( DataRepository.login(user.value!!))
            state.value=DataRepository.login(user.value!!)
        }
        return state
    }
}
