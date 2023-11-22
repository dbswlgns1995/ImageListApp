package com.example.imagelistapp.data.source.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.imagelistapp.MyApplication
import com.example.imagelistapp.data.model.Image
import com.example.imagelistapp.data.model.SearchResponse
import com.example.imagelistapp.data.source.remote.service.SearchService

class MainPagingSource(
    private val query: String,
    private val searchService: SearchService
): PagingSource<Int, Image>() {

    private var lastQuery: String = ""

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        try {
            val page =
                if(lastQuery == query) {
                    params.key ?: 1
                } else {
                    lastQuery = query
                    1
                }

            val size = params.loadSize

            val loadImageList = searchService.loadImageList(query = query, page = page, perPage = size)

            val result = loadImageList.results.map { result ->
                Image(url = result.urls.regular, color = result.color, id = result.id)
            }

            return LoadResult.Page(
                data = result,
                prevKey = if (page == 1) null else page -1,
                nextKey = if (result.isEmpty()) null else page + 1
            )
        } catch (e: java.lang.Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}