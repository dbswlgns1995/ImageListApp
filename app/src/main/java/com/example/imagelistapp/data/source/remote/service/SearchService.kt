package com.example.imagelistapp.data.source.remote.service

import com.example.imagelistapp.data.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("search/photos")
    suspend fun loadImageList(
        @Query("query") query: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
    ): SearchResponse
}