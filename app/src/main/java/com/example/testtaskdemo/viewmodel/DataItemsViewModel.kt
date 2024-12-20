package com.example.testtaskdemo.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtaskdemo.data.impl.WeatherPrefs
import com.example.testtaskdemo.data.pojo.WeatherObj
import com.example.testtaskdemo.data.repo.WeatherRepo
import com.example.testtaskdemo.data.setup.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.prefs.Preferences
import javax.inject.Inject
@HiltViewModel
class DataItemsViewModel @Inject constructor(private val weatherRepo: WeatherRepo,private val pref:WeatherPrefs):ViewModel() {
    private val weatherResult= MutableStateFlow<Response<WeatherObj>>(Response.IDLE())
    val _weatherResult=weatherResult.asStateFlow()
    var getCurrent= mutableStateOf<WeatherObj?>(null)
    init {
        viewModelScope.launch {
            getCurrent.value=pref.getWeather()
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getWeather(search:String) {
        viewModelScope.launch {
            weatherResult.value= Response.IDLE()
            withContext(Dispatchers.IO) {
                weatherRepo.getWeather(search).collect{
                    weatherResult.value=it
                }

            }
        }
    }
    suspend fun saveObject(weather:WeatherObj){
        pref.setWeather(weather)
        viewModelScope.launch {
            getCurrent.value=pref.getWeather()
        }
    }

    fun reset() {
        weatherResult.value=Response.IDLE()
    }

}