package com.jhonjto.android_bold.data

import com.jhonjto.android_bold.data.entities.ForecastResponse
import com.jhonjto.android_bold.data.entities.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/v1/search.json?")
    suspend fun getWeather(@Query("q") location: String): Response<SearchResponse>

    @GET("/v1/forecast.json?")
    suspend fun getForecast(@Query("q") location: String, @Query("days") days: Int): Response<ForecastResponse>
}
