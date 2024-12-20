package com.example.testtaskdemo.data.impl

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.testtaskdemo.data.pojo.WeatherObj
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.json.JSONObject

class WeatherPrefs(val context: Context) {
    val Context.datastore by preferencesDataStore(
        name = "prefs"
    )
     suspend fun setWeather(weather:WeatherObj) {
        context.datastore.edit {prefs->
            prefs[stringPreferencesKey(PrefKeys.SAVED_WEATHER)]= getJSONString(weather)!!
        }
    }
    suspend fun getWeather(): WeatherObj?{
         val dataStoreKey = stringPreferencesKey(PrefKeys.SAVED_WEATHER)

             val preferences = context.datastore.data.first()
        preferences[dataStoreKey]?.let {
            return convertJsonToObject(it)
        }
         return null

    }
}
object PrefKeys{
    const val SAVED_WEATHER="saved_weather"
}
inline fun <reified T> convertJsonToObject(jsonString: String): T? {
    return try {
        val gson = Gson()
        gson.fromJson(jsonString, T::class.java)
    } catch (e: Exception) {
        Log.e("formData","${e}")
        e.printStackTrace()
        null
    }
}






inline fun <reified T> getJSONString(value: T): String? {
    return try {
        val gson = Gson()
        val jsonString = gson.toJson(value)
        return JSONObject(jsonString).toString()
    } catch (e: Exception) {
        null
    }
}