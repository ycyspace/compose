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
    fun getUser(@Query("phone_number") phone_number: String): Call<User>
    @POST("/regist")
    fun addUser(@Body user: User):Call<Status>
    @POST("/login")
    fun login(@Body user: User):Call<Status>
    @POST
    fun Planning(@Body planningData: PlanningData):Call<List<SlightVo>>
}