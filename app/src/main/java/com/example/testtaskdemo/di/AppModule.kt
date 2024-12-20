package com.example.testtaskdemo.di

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi

import com.example.testtaskdemo.data.api.Urls
import com.example.testtaskdemo.data.api.WeatherApi
import com.example.testtaskdemo.data.impl.WeatherPrefs
import com.example.testtaskdemo.data.impl.WeatherRepoImpl
import com.example.testtaskdemo.data.repo.WeatherRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideOkHttpClient(
    ): OkHttpClient {
        val headerInterceptor = Interceptor { chain ->
            val original = chain.request()
            val newUrl=original.url.newBuilder().build()
            val requestBuilder = original.newBuilder()
                .header("Content-Type", "application/json")
                .url(newUrl)
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        return OkHttpClient.Builder()
            .addInterceptor(headerInterceptor).also {
                it.addInterceptor(
                    HttpLoggingInterceptor().setLevel(
                        HttpLoggingInterceptor.Level.BODY
                    ))
            }
            .connectTimeout(Duration.ofSeconds(Urls.TIME_OUT))
            .readTimeout(Duration.ofSeconds(Urls.TIME_OUT))
            .writeTimeout(Duration.ofSeconds(Urls.TIME_OUT))
            .retryOnConnectionFailure(true)
            .build()
    }
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Urls.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun getMovieApi(retrofit: Retrofit):WeatherApi{
        return retrofit.create(WeatherApi::class.java)
    }
    @Provides
    @Singleton
    fun getTaskRepo(dataGroupItemApi: WeatherApi):WeatherRepo{
        return WeatherRepoImpl(dataGroupItemApi)
    }


    @Provides
    @Singleton
    fun getWeatherPrefs(application: Application)= WeatherPrefs(application)


}
