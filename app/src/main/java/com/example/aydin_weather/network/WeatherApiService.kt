package com.example.aydin_weather.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * AYDIN_Weather created by aydin
 * student ID : 991521740
 * on 20/11/20 */

private var baseUrl = "https://api.aerisapi.com/observations/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(MoshiConverterFactory.create(moshi)).build()

interface WeatherApiService {
    @GET
    suspend fun retrieveResponse(@Url apiUrl: String) : Weather  //uses dynamic url
}

object WeatherApi{
    val RETROFIT_SERVICE_WEATHER : WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}