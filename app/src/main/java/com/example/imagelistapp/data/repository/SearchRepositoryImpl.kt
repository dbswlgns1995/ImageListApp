package com.example.imagelistapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.imagelistapp.data.model.Image
import com.example.imagelistapp.data.source.remote.MainPagingSource
import com.example.imagelistapp.data.source.remote.service.SearchService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchService: SearchService
): SearchRepository  {

    override fun loadImageList(query: String): Flow<PagingData<Image>> {
        return Pager(
            PagingConfig(
                pageSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory =  {
                MainPagingSource(query ,searchService)
            }
        ).flow
    }

    companion object {
        const val TAG = "SearchRepositoryImpl.MyLog"
    }
}