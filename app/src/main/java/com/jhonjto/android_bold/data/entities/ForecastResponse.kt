package com.jhonjto.android_bold.data.entities

data class ForecastResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)