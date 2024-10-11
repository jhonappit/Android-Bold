package com.jhonjto.android_bold.domain

import com.jhonjto.android_bold.data.entities.ForecastResponse

interface ForecastRepository {
    suspend fun getForecast(location: String, days: Int): Result<ForecastResponse>
}
