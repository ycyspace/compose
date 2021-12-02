package com.ye.compose.model


data class PlanningData(
    val dayNum:Int,
    val time:Double,
    val lng:Double,
    val lat:Double,
    val mustSlight: List<Slight>,
    val noSlight: List<Slight>,
    val tags:List<String>,
    val city:String,
)
