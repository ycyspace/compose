package com.ye.compose.model


data class User(
    val age: Int=0,
    val uid:Int=-1,
    val city: String="",
    val gender: Int=0,
    val head_img: String="",
    val name: String="",
    val password: String,
    val phone_number: String,
    val province: String="",
    val register_date:String="",
    val role_id: Int=0,
//    val tags: List<String>
)