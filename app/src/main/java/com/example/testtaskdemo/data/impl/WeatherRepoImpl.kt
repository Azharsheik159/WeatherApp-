package com.example.testtaskdemo.data.impl

import android.util.Log
import com.example.testtaskdemo.data.api.WeatherApi
import com.example.testtaskdemo.data.pojo.WeatherErrorObj
import com.example.testtaskdemo.data.pojo.WeatherObj
import com.example.testtaskdemo.data.repo.WeatherRepo
import com.example.testtaskdemo.data.setup.Response
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class WeatherRepoImpl @Inject constructor(val weatherApi: WeatherApi):WeatherRepo {
    val NO_DATA_FOUND="No data found"

    override suspend fun getWeather(keyword:String): Flow<Response<WeatherObj>> {
        return flow {
            emit(Response.Loading())
            try {
                val response=weatherApi.getDataItems(keyword)
                if (response.isSuccessful) {
                    if(response.body()!=null){
                        emit(Response.Success(response.body()!!))
                    }else{
                        emit(Response.Error(NO_DATA_FOUND))
                    }
                } else {
                    val errorData=response.errorBody()?.string()
                    val errorDataClass: WeatherErrorObj = Gson().fromJson(errorData, WeatherErrorObj::class.java)
                    emit(Response.Error(errorDataClass.error.message))
                }
            } catch (e: Exception) {
                emit(Response.Error("${e}"))

            }
        }

    }
}


