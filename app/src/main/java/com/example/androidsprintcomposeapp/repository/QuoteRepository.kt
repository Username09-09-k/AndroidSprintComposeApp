package com.example.androidsprintcomposeapp.repository

import com.example.androidsprintcomposeapp.api.RetrofitInstance
import com.example.androidsprintcomposeapp.data.Quote

class QuoteRepository {
    suspend fun getQuote(): List<Quote> {
        return RetrofitInstance.quoteApi.getQuote()
    }
}