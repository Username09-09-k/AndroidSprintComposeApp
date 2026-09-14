package com.example.androidsprintcomposeapp.api

import com.example.androidsprintcomposeapp.data.Quote
import retrofit2.http.GET

interface QuoteApi {
    @GET("api/random")
    suspend fun getQuote(): List<Quote> // modern style - coroutines
}