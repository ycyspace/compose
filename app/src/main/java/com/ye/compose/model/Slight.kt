package com.ye.compose.model


data class Slight(
    val name: String,
    val lat: Double,
    val lng: Double,
    val country: String,
    val address: String,
    val province: String,
    val city: String,
    val area: String,
    val level: String,
    val sid:Int,
    val spend_time:Double,
    val price:Double,
    val business_time:String,
    val over_rating:Double,
    val comment_num:Int,
    val best_time_start:Int,
    val best_time_end:Int,
    val tags:List<String>,
    val slight_pictures:List<String>,
    val detail:String,
    val phone_number:String
)
