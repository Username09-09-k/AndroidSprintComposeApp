package com.example.androidsprintcomposeapp.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    // Waiting time
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS) // подключение
        .readTimeout(30, TimeUnit.SECONDS)    // чтение данных
        .writeTimeout(30, TimeUnit.SECONDS)   // отправка данных
        .build()

    // Quote API
    private val retrofitQuote = Retrofit.Builder()
        .baseUrl("https://zenquotes.io/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val quoteApi: QuoteApi = retrofitQuote.create(QuoteApi::class.java)

    // Joke API
    private val retrofitJoke = Retrofit.Builder()
        .baseUrl("https://v2.jokeapi.dev/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val jokeApi: JokeApi = retrofitJoke.create(JokeApi::class.java)

    // FastAPI realises example video
    private val retrofitVideo = Retrofit.Builder()
        .baseUrl("http://192.168.1.151:8000/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val videoApi: VideoApi = retrofitVideo.create(VideoApi::class.java)
}