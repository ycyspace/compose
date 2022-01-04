package com.ye.compose.model


data class PlanningData(
    val dayNum:Int,
    val time:Long,
    val lng:Double,
    val lat:Double,
    val mustSlight: MutableList<Slight>,
    val noSlight: MutableList<Slight>,
    val tags:MutableList<String>,
    val city:String,
)
