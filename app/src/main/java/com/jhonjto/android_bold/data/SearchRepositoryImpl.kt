package com.jhonjto.android_bold.data

import com.jhonjto.android_bold.data.entities.SearchResponse
import com.jhonjto.android_bold.domain.SearchRepository

class SearchRepositoryImpl(private val weatherApi: WeatherApi) : SearchRepository {
    override suspend fun getSearch(location: String): Result<SearchResponse> {
        return try {
            val response = weatherApi.getWeather(location)
            val searchResponse = response.body()
            if (searchResponse != null) {
                Result.success(searchResponse)
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
