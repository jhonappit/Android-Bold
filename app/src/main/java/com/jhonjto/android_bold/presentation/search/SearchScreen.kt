package com.jhonjto.android_bold.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.jhonjto.android_bold.R
import com.jhonjto.android_bold.data.entities.ForecastResponse
import com.jhonjto.android_bold.ui.theme.Blue7EA1C4
import com.jhonjto.android_bold.ui.theme.Blue87ACC9
import com.jhonjto.android_bold.ui.theme.Blue90AFCB

@Preview(showBackground = true)
@Composable
fun SearchScreen() {
    val viewModel = hiltViewModel<WeatherViewModel>()

    val state = viewModel.state.value
    val forecastState = viewModel.forecastState.value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Blue7EA1C4)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .background(color = Blue7EA1C4), contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(id = R.string.developer),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(id = R.string.type),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .background(color = Blue87ACC9)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Blue87ACC9),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = viewModel.query,
                    onValueChange = { viewModel.onQueryChanged(it) },
                    label = { Text("Search Location") },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            viewModel.getForecast()
                        }
                    ),
                    trailingIcon = {
                        if (viewModel.query.isNotEmpty()) {
                            androidx.compose.material3.IconButton(
                                onClick = {
                                    viewModel.resetQuery()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = "Clear"
                                )

                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                when {
                    state.isLoading -> {
                        CircularProgressIndicator()
                    }

                    state.error != null -> {
                        Text(
                            text = state.error,
                            color = Color.Red
                        )
                    }

                    state.searchResponse != null -> {
                        state.searchResponse.forEach { searchResponse ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxSize()
                                    .background(color = Blue87ACC9)
                            ) {
                                Text("Location: ${searchResponse.name}, ${searchResponse.country}")
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }
            }
        }
        when {
            forecastState.isLoading -> {
                CircularProgressIndicator()
            }

            forecastState.error != null -> {
                Text(
                    text = forecastState.error,
                    color = Color.Red
                )
            }

            forecastState.forecastResponse != null -> {
                DetailedWeatherView(forecastResponse = forecastState.forecastResponse)
            }
        }
    }
}

@Composable
fun DetailedWeatherView(forecastResponse: ForecastResponse) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .background(color = Blue90AFCB)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${forecastResponse.location.name}, ${forecastResponse.location.country}",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(color = Blue87ACC9)
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = forecastResponse.current.condition.text,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Current Temp: ${forecastResponse.current.temp_c}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    AsyncImage(
                        model = forecastResponse.current.condition.icon,
                        contentDescription = "Current Condition Icon",
                        modifier = Modifier.size(64.dp)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn {
                    itemsIndexed(forecastResponse.forecast.forecastday) { index, _ ->
                        WeatherDayInfo(forecastResponse = forecastResponse, index = index)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherDayInfo(forecastResponse: ForecastResponse, index: Int) {
    val dayForecast = forecastResponse.forecast.forecastday[index]

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = "Date: ${dayForecast.date}", style = MaterialTheme.typography.headlineSmall)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .background(color = Blue87ACC9)
        ) {
            AsyncImage(
                model = dayForecast.day.condition.icon,
                contentDescription = "Weather Condition Icon",
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = dayForecast.day.condition.text, style = MaterialTheme.typography.bodySmall)
                Text(
                    text = "Avg Temp: ${dayForecast.day.avgtemp_c}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
