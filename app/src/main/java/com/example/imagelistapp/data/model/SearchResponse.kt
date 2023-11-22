package com.example.imagelistapp.data.model

data class SearchResponse(
    val results: List<Result>,
    val total: Int,
    val total_pages: Int,
)