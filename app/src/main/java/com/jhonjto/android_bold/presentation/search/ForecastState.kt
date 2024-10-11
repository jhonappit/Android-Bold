package com.jhonjto.android_bold.presentation.search

import com.jhonjto.android_bold.data.entities.ForecastResponse

data class ForecastState(
    val isLoading: Boolean = false,
    val forecastResponse: ForecastResponse? = null,
    val error: String? = null
)
