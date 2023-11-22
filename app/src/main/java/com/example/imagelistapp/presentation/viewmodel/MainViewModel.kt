package com.example.imagelistapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.imagelistapp.data.model.Image
import com.example.imagelistapp.data.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchRepository: SearchRepository
): ViewModel() {

    private var query: String? = null
    var imageList: Flow<PagingData<Image>>? = null

    fun searchList(newQuery: String): Flow<PagingData<Image>>{
        val lastList = imageList
        if (this.query == newQuery && lastList != null) {
            return lastList
        }
        this.query = newQuery
        val newResult = searchRepository.loadImageList(newQuery)
            .cachedIn(viewModelScope)
        imageList = newResult

        return newResult
    }

    companion object {
        const val TAG = "MainViewModel.MyLog"
    }
}