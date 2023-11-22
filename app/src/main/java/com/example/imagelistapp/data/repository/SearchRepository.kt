package com.example.imagelistapp.data.repository

import androidx.paging.PagingData
import com.example.imagelistapp.data.model.Image
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun loadImageList(query: String): Flow<PagingData<Image>>
}