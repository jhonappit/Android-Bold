package com.jhonjto.android_bold.domain

import com.jhonjto.android_bold.data.entities.ForecastResponse
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(private val forecastRepository: ForecastRepository) {
    suspend operator fun invoke(location: String, days: Int): Result<ForecastResponse> {
        return forecastRepository.getForecast(location, days)
    }
}
