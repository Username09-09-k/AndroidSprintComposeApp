package com.example.androidsprintcomposeapp.repository

import com.example.androidsprintcomposeapp.api.RetrofitInstance
import com.example.androidsprintcomposeapp.data.Joke

class JokeRepository {
    suspend fun getJoke(): Joke {
        return RetrofitInstance.jokeApi.getJoke()
    }
}