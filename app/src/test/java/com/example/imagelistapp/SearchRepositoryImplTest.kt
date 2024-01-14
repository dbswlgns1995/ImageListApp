package com.example.imagelistapp

import androidx.paging.PagingData
import app.cash.turbine.test
import com.example.imagelistapp.data.model.*
import com.example.imagelistapp.data.repository.SearchRepository
import com.example.imagelistapp.data.repository.SearchRepositoryImpl
import com.example.imagelistapp.data.source.remote.service.SearchService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
@Suppress("NonAsciiCharacters")
class SearchRepositoryImplTest {


    private lateinit var searchRepository: SearchRepository

    @Mock
    private lateinit var searchService: SearchService

    private lateinit var autoCloseable: AutoCloseable

    private lateinit var searchResponse: List<Result>

    @Mock
    private lateinit var imageResponse: List<Image>

    @Mock
    private lateinit var result: Result



    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        autoCloseable = MockitoAnnotations.openMocks(this)

        `when`(result.blur_hash).thenReturn("hashExample")
        `when`(result.color).thenReturn("colorExample")
        `when`(result.created_at).thenReturn("timeExample")
        `when`(result.current_user_collections).thenReturn(emptyList())
        `when`(result.description).thenReturn("catExample")
        `when`(result.height).thenReturn(4032)
        `when`(result.id).thenReturn("idExample")
        `when`(result.liked_by_user).thenReturn(true)
        `when`(result.likes).thenReturn(100)
        `when`(result.links).thenReturn(Links("downloadExample", "HtmlExample", "selfExample"))
        `when`(result.urls).thenReturn(Urls("","","", "", ""))
        `when`(result.user).thenReturn(
            User(first_name="", id="", instagram_username="", last_name="", links= LinksX(html="", likes="", photos="", self="https://api.unsplash.com/users/cyrus_c"),
                name="", portfolio_url="", profile_image=ProfileImage(large="", medium="", small=""), twitter_username="", username="cyrus_c"))
        `when`(result.width).thenReturn(4032)

        searchResponse = listOf(result)

        imageResponse = searchResponse.map { result ->
            Image(url = result.urls.regular, color = result.color, id = result.id)
        }

        searchRepository = SearchRepositoryImpl(searchService)
    }

    @After
    fun close() {
        Dispatchers.resetMain()
        autoCloseable.close()
    }

    @Test
    fun `loadImageList should return PagingData of Image`() = runTest {

        val query = "cat"
        val fakeImageList = searchResponse
        val pagingDataResult = PagingData.from(imageResponse)

        `when`(searchService.loadImageList(query, 1, 30)).thenReturn(SearchResponse(fakeImageList,0, 0))

        val flow = searchRepository.loadImageList(query)

        flow.test {
            assertThat(awaitItem()).isInstanceOf(pagingDataResult.javaClass)
        }

    }


}

