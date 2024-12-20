package com.example.testtaskdemo.data.pojo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherObj(val location:LocationObj, val current:CurrentObj):Serializable
data class LocationObj(
    val name: String,
    val region: String,
    val country:String

)


data class CurrentObj(
    @SerializedName("temp_c") val temp:Double,
    val humidity:Double,
    @SerializedName("feelslike_c")val feelsLike:Double,
    val uv:Double,
    val condition:ConditionObj
)
data class ConditionObj (
    val text:String,
    val icon:String
)
data class WeatherErrorObj(val error:ErrorOBJ):Serializable
data class ErrorOBJ (
    val message:String,
    val code:Int
)

