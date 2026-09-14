package com.example.androidsprintcomposeapp.api

import com.example.androidsprintcomposeapp.data.Video
import retrofit2.http.GET

interface VideoApi {
    @GET("videos/1")
    suspend fun getVideo(): Video // modern style - coroutines
}