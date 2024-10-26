package com.example.androidassignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example.androidassignment.data.article.ArticleRepository
import com.example.androidassignment.data.beer.BeerRepository
import com.example.androidassignment.data.beer.RetrofitInstance
import com.example.androidassignment.presentation.viewmodel.ArticleViewModelFactory
import com.example.androidassignment.presentation.viewmodel.BeerViewModelFactory


object Injection {
    /**
     * Creates an instance of [ArticleRepository]
     */
    private fun provideArticleRepository(): ArticleRepository = ArticleRepository()

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(owner: SavedStateRegistryOwner): ViewModelProvider.Factory {
        return ArticleViewModelFactory(owner, provideArticleRepository())
    }

    private fun provideBeerRepository(): BeerRepository = BeerRepository(RetrofitInstance.beerApi)

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideBeerViewModelFactory(owner: SavedStateRegistryOwner): ViewModelProvider.Factory {
        return BeerViewModelFactory(owner, provideBeerRepository())
    }
}