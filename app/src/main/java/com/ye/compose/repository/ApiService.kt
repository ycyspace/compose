package com.ye.compose.repository

import android.text.Html
import com.ye.compose.model.PlanningData
import com.ye.compose.model.SlightVo
import com.ye.compose.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("/profile")
    suspend fun getUser(@Query("phone_number") phone_number: String): User
    @POST("/regist")
    suspend fun addUser(@Body user: User):Status
    @POST("/login")
    suspend fun login(@Body user: User):Status
    @POST("/getPlan")
    suspend fun planning(@Body planningData: PlanningData):List<SlightVo>
}