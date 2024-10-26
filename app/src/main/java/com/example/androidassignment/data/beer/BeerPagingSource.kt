package com.example.androidassignment.data.beer

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

class BeerPagingSource(val beerApi: BeerApi) : PagingSource<Int, BeerDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BeerDto> {
        val page = params.key ?: 1
        return try {
            val beers = beerApi.getBeers(page = page, pageCount = 10)
            LoadResult.Page(
                data = beers,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (beers.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, BeerDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
