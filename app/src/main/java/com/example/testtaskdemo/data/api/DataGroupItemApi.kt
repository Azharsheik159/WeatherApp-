package com.example.testtaskdemo.data.api


import com.example.testtaskdemo.data.pojo.WeatherObj
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET(Urls.DATA_ITEM_ENDPOINT)
    suspend fun getDataItems(@Query("q") search:String,
                             @Query("key") key:String=Urls.API_KEY):retrofit2.Response<WeatherObj>

}