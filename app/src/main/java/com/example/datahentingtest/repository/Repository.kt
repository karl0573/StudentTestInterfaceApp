package com.example.datahentingtest.repository

import com.example.datahentingtest.api.RetrofitInstance
import com.example.datahentingtest.model.*
import retrofit2.Response

class Repository {
    suspend fun getProve(verdi: Int, verdi2: String): Response<Prove> {
        return RetrofitInstance.api.getProve(verdi, verdi2)
    }

    suspend fun getAlleProver(): Response<RecordsKort> {
        return RetrofitInstance.api.getAlleProver()
    }

    suspend fun getProven(verdier: String): Response<RecordsTest> {
        return RetrofitInstance.api.getProven(verdier)
    }
    
    suspend fun getBruker(verdi: String): Response<RecordsBruker> {
        return RetrofitInstance.api.getBruker(verdi)
    }
}