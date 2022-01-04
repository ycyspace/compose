package com.ye.compose.repository

import android.util.Log
import com.ye.compose.MyApplication.Companion.apiService
import com.ye.compose.model.PlanningData
import com.ye.compose.model.SlightVo
import com.ye.compose.model.User
import com.ye.compose.utils.await
import com.ye.compose.utils.toastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException


class DataRepository {
    companion object {
        private val error = Status("连接超时，请检查网络", 5)
        suspend fun getUser(phone_number: String): User {
            return withContext(Dispatchers.IO) {
                apiService.getUser(phone_number)
            }
        }
        suspend fun addUser(user: User): Status {
            return withContext(Dispatchers.IO) {
                try {
                    apiService.addUser(user)
                } catch (e: Exception) {
                    error
                }
            }
        }
        suspend fun login(user: User): Status {
            return withContext(Dispatchers.IO) {
                try {
                    apiService.login(user)
                } catch (e: Exception) {
                    error
                }
            }
        }
        suspend fun getPlan(planningData: PlanningData):Any{
            return withContext(Dispatchers.IO){
                try {
                    apiService.planning(planningData)
                }catch (e:Exception){
                    error
                }

            }
        }
    }
}