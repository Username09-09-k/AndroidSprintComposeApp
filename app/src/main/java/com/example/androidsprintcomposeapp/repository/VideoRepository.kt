package com.example.androidsprintcomposeapp.repository

import com.example.androidsprintcomposeapp.api.RetrofitInstance
import com.example.androidsprintcomposeapp.data.Video

class VideoRepository {
    suspend fun getVideo(): Video {
        return RetrofitInstance.videoApi.getVideo()
    }
}
