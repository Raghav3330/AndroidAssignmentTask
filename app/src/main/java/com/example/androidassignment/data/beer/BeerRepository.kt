package com.example.androidassignment.data.beer

import androidx.paging.PagingSource
import com.example.androidassignment.data.article.Article
import com.example.androidassignment.data.article.ArticlePagingSource

class BeerRepository(val beerApi: BeerApi) {
    /**
     * [PagingSource] for [Article]
     */
    fun beerPagingSource() = BeerPagingSource(beerApi)
}