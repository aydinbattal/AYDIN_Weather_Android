package com.example.aydin_weather.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * AYDIN_Weather created by aydin
 * student ID : 991521740
 * on 20/11/20 */
@JsonClass(generateAdapter = true)
data class Weather (
    val response: Response?
)

data class Response (
    @Json(name = "loc") val location: Location?  = null,
    @Json(name = "ob") val obtained: Obtained?  = null
)

data class Location (
    @Json(name = "lat") val latitude : Double? = null,
    @Json(name = "long") val long : Double? = null
)

data class Obtained (
    @Json(name = "tempC") val degree : Double? = null,
    @Json(name = "feelslikeC") val degreeFeels : Double? = null,
    @Json(name = "humidity") val humidity : Int? = null,
    @Json(name = "windKPH") val windSpeed : Int? = null,
    @Json(name = "windDir") val windDirection : String? = null,
    @Json(name = "visibilityKM") val visibility : Double? = null
)