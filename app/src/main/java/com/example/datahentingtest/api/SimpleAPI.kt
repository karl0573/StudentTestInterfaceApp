package com.example.datahentingtest.api

import com.example.datahentingtest.model.Post
import com.example.datahentingtest.model.Prove
import retrofit2.Response
import retrofit2.http.GET
interface SimpleAPI {
    @GET("BrukerQuiz/GameOfThrones")
    suspend fun getPost(): Response<Post>
    @GET("GameOfThrones/1")
    suspend fun getProve(): Response<Prove>
}