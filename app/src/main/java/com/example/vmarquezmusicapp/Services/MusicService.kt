package com.example.vmarquezmusicapp.Services

import com.example.vmarquezmusicapp.Models.Music
import retrofit2.http.GET
import retrofit2.http.Path

interface MusicService {
    @GET("albums")
    suspend fun getAllMusic(): List<Music>
    @GET("albums/{id}")
    suspend fun getMusicById(@Path("id") id: String): Music
}