package com.example.testtaskdemo.data.repo

import com.example.testtaskdemo.data.pojo.WeatherObj
import com.example.testtaskdemo.data.setup.Response
import kotlinx.coroutines.flow.Flow

interface WeatherRepo {
    suspend fun getWeather(keyword:String): Flow<Response<WeatherObj>>
}