package com.example.androidassignment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.androidassignment.data.beer.BeerPagingSource
import com.example.androidassignment.data.beer.BeerRepository

class BeerViewModel (private val repository: BeerRepository) : ViewModel() {

    fun getBeers() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = { repository.beerPagingSource() }
    ).liveData
}