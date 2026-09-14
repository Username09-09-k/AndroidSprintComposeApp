package com.example.androidsprintcomposeapp.api

import com.example.androidsprintcomposeapp.data.Joke
import retrofit2.http.GET

interface JokeApi {
    @GET("joke/Any")
    suspend fun getJoke(): Joke // modern style - coroutines
}