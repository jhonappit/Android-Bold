package com.jhonjto.android_bold.data

import com.jhonjto.android_bold.data.entities.ForecastResponse
import com.jhonjto.android_bold.domain.ForecastRepository

class ForecastRepositoryImpl(private val weatherApi: WeatherApi) : ForecastRepository {
    override suspend fun getForecast(location: String, days: Int): Result<ForecastResponse> {
        return try {
            val response = weatherApi.getForecast(location, days)
            val forecastResponse = response.body()
            if (forecastResponse != null) {
                Result.success(forecastResponse)
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
