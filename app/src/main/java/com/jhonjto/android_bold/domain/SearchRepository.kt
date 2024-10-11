package com.jhonjto.android_bold.domain

import com.jhonjto.android_bold.data.entities.SearchResponse

interface SearchRepository {
    suspend fun getSearch(location: String): Result<SearchResponse>
}
