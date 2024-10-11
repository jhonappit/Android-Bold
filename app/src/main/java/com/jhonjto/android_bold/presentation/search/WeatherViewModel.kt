package com.jhonjto.android_bold.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhonjto.android_bold.domain.GetForecastUseCase
import com.jhonjto.android_bold.domain.GetSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getSearchUseCase: GetSearchUseCase,
    private val getForecastUseCase: GetForecastUseCase
): ViewModel() {
    var query by mutableStateOf("")
    val state = mutableStateOf(WeatherState())
    val forecastState = mutableStateOf(ForecastState())

    init {
        viewModelScope.launch {
            snapshotFlow { query }
                .debounce(500)
                .collect { queryText ->
                    if (queryText.isNotBlank()) {
                        getWeather()
                    }
                }
        }
    }

    fun onQueryChanged(newQuery: String) {
        query = newQuery
    }

    fun getWeather() {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true, error = null)

            val result = getSearchUseCase.invoke(query)
            result.onSuccess { searchResponse ->
                state.value = state.value.copy(
                    isLoading = false,
                    searchResponse = searchResponse,
                    error = null
                )
            }.onFailure { exception ->
                state.value = state.value.copy(
                    isLoading = false,
                    error = "Error fetching weather data: ${exception.message}"
                )
            }
        }
    }

    fun getForecast() {
        viewModelScope.launch {
            forecastState.value = forecastState.value.copy(isLoading = true, error = null)

            val result = getForecastUseCase.invoke(query, 3)
            result.onSuccess { forecastResponse ->
                forecastState.value = forecastState.value.copy(
                    isLoading = false,
                    forecastResponse = forecastResponse,
                    error = null
                )
            }.onFailure {
                forecastState.value = forecastState.value.copy(
                    isLoading = false,
                    error = "Error fetching forecast data: ${it.message}"
                )
            }
        }
    }

    fun resetQuery() {
        query = ""
        state.value = state.value.copy(searchResponse = null)
        forecastState.value = forecastState.value.copy(forecastResponse = null)
    }
}
