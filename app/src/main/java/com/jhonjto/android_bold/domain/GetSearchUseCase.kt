package com.jhonjto.android_bold.domain

import com.jhonjto.android_bold.data.entities.SearchResponse
import javax.inject.Inject

class GetSearchUseCase @Inject constructor(private val searchRepository: SearchRepository) {
    suspend operator fun invoke(location: String): Result<SearchResponse> {
        return searchRepository.getSearch(location)
    }
}
