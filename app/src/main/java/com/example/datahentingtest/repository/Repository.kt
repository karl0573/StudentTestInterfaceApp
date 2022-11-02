package com.example.datahentingtest.repository

import com.example.datahentingtest.api.RetrofitInstance
import com.example.datahentingtest.model.Post
import com.example.datahentingtest.model.Prove
import retrofit2.Response

class Repository {

    suspend fun getPost(): Response<Post> {
        return RetrofitInstance.api.getPost()
    }
    suspend fun getProve(): Response<Prove> {
        return RetrofitInstance.api.getProve()
    }

}