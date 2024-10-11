package com.jhonjto.android_bold

import com.jhonjto.android_bold.data.SearchResponseItem
import com.jhonjto.android_bold.data.entities.SearchResponse
import com.jhonjto.android_bold.domain.GetSearchUseCase
import com.jhonjto.android_bold.domain.SearchRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UseCaseTest {

    @MockK
    private lateinit var searchRepository: SearchRepository

    private lateinit var getSearchUseCase: GetSearchUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getSearchUseCase = GetSearchUseCase(searchRepository)
    }

    @Test
    fun `invoke should return search response from repository`() = runTest {
        val location = "London"
        val searchResponseItem = SearchResponseItem(
            name = "Uk",
            id = 1,
            lat = 1.0,
            lon = 1.0,
            region = "London",
            country = "London",
            url = "https://example.com"
        )

        val searchResponse = SearchResponse()

        searchResponse.add(searchResponseItem)

        coEvery { searchRepository.getSearch(location) } returns Result.success(searchResponse)

        val result = getSearchUseCase.invoke(location)
        assertEquals(Result.success(searchResponse), result)
    }

    @Test
    fun `invoke should return error from repository`() = runTest {
        val location = "London"
        val exception = Exception("Network error")

        coEvery { searchRepository.getSearch(location) } returns Result.failure(exception)

        val result = getSearchUseCase.invoke(location)

        assertEquals(Result.failure<SearchResponse>(exception), result)
    }


}
