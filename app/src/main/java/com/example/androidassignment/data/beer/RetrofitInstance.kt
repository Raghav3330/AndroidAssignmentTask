package com.example.androidassignment.data.beer

import com.example.androidassignment.data.beer.BeerApi.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val retrofitInstance = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    val beerApi = retrofitInstance.create(BeerApi::class.java)
}