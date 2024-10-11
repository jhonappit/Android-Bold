package com.jhonjto.android_bold.presentation.search

import com.jhonjto.android_bold.data.entities.SearchResponse

data class WeatherState(
    val isLoading: Boolean = false,
    val searchResponse: SearchResponse? = null,
    val error: String? = null
)
