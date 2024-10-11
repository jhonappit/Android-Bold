package com.jhonjto.android_bold

import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import com.jhonjto.android_bold.data.SearchResponseItem
import com.jhonjto.android_bold.data.entities.ForecastResponse
import com.jhonjto.android_bold.data.entities.SearchResponse
import com.jhonjto.android_bold.domain.GetForecastUseCase
import com.jhonjto.android_bold.domain.GetSearchUseCase
import com.jhonjto.android_bold.presentation.search.WeatherViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var getSearchUseCase: GetSearchUseCase

    @MockK
    private lateinit var getForecastUseCase: GetForecastUseCase

    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = WeatherViewModel(getSearchUseCase, getForecastUseCase)
    }

    @After
    fun tearDown() {
        mainCoroutineRule.runCurrent()
    }

    @Test
    fun `initial state is correct`() = runTest {
        assertEquals("", viewModel.query)
        assertEquals(false, viewModel.state.value.isLoading)
        assertEquals(null, viewModel.state.value.error)
        assertEquals(null, viewModel.state.value.searchResponse)
        assertEquals(false, viewModel.forecastState.value.isLoading)
        assertEquals(null, viewModel.forecastState.value.error)
        assertEquals(null, viewModel.forecastState.value.forecastResponse)
    }

    @Test
    fun `onQueryChanged updates query`() = runTest {
        viewModel.onQueryChanged("London")
        assertEquals("London", viewModel.query)
    }

    @Test
    fun `getWeather updates state with search response`() = runTest {
        val searchResponseItem = SearchResponseItem(
            name = "London",
            id = 1,
            lat = 1.0,
            lon = 1.0,
            region = "London",
            country = "UK",
            url = "https://example.com"
        )

        val searchResponse = SearchResponse()

        searchResponse.add(searchResponseItem)
        coEvery { getSearchUseCase.invoke("London") } returns Result.success(searchResponse)

        viewModel.onQueryChanged("London")
        viewModel.getWeather()

        assertEquals(searchResponse, viewModel.state.value.searchResponse)
        assertEquals(false, viewModel.state.value.isLoading)
        assertEquals(null, viewModel.state.value.error)
    }

    @Test
    fun `getWeather updates state with error`() = runTest {
        val exception = Exception("Network error")
        coEvery { getSearchUseCase.invoke("London") } returns Result.failure(exception)

        viewModel.onQueryChanged("London")
        viewModel.getWeather()

        assertEquals(null, viewModel.state.value.searchResponse)
        assertEquals(false, viewModel.state.value.isLoading)
        assertEquals("Error fetching weather data: Network error", viewModel.state.value.error)
    }

    @Test
    fun `getForecast updates forecastState with forecast response`() = runTest {
        val forecastResponse = mockk<ForecastResponse>()
        coEvery { getForecastUseCase.invoke("London", 3) } returns Result.success(forecastResponse)

        viewModel.onQueryChanged("London")
        viewModel.getForecast()

        assertEquals(forecastResponse, viewModel.forecastState.value.forecastResponse)
        assertEquals(false, viewModel.forecastState.value.isLoading)
        assertEquals(null, viewModel.forecastState.value.error)
    }

    @Test
    fun `getForecast updates forecastState with error`() = runTest {
        val exception = Exception("Network error")
        coEvery { getForecastUseCase.invoke("London", 3) } returns Result.failure(exception)

        viewModel.onQueryChanged("London")
        viewModel.getForecast()

        assertEquals(null, viewModel.forecastState.value.forecastResponse)
        assertEquals(false, viewModel.forecastState.value.isLoading)
        assertEquals("Error fetching forecast data: Network error", viewModel.forecastState.value.error)
    }

    @Test
    fun `resetQuery resets query and state`() = runTest {
        viewModel.onQueryChanged("London")
        viewModel.state.value = viewModel.state.value.copy(searchResponse = mockk())
        viewModel.forecastState.value = viewModel.forecastState.value.copy(forecastResponse = mockk())

        viewModel.resetQuery()

        assertEquals("", viewModel.query)
        assertEquals(null, viewModel.state.value.searchResponse)
        assertEquals(null, viewModel.forecastState.value.forecastResponse)
    }
}
