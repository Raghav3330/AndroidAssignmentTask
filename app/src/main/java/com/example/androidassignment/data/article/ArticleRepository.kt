package com.example.androidassignment.data.article
import androidx.paging.PagingSource

/**
 * Repository class that mimics fetching [Article] instances from an asynchronous source.
 */
class ArticleRepository {
    /**
     * [PagingSource] for [Article]
     */
    fun articlePagingSource() = ArticlePagingSource()
}