package com.example.datahentingtest.api
import com.example.datahentingtest.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SQLAPI {
    @GET("BrukerQuiz/GameOfThrones")
    suspend fun getPost(): Response<Kort>

    @GET("{proveNavn}/{SporsmalNr}")
    suspend fun getProve(@Path("SporsmalNr") SporsmalNr: Int,
                         @Path("proveNavn") proveNavn: String
                         ): Response<Prove>

    @GET("BrukerQuiz")
    suspend fun getAlleProver(): Response<RecordsKort>

    @GET("{proveNavn}")
    suspend fun getProven(@Path("proveNavn") proveNavn: String): Response<RecordsTest>

    @GET("users?filter=usersUid,eq,{brukerNavn}&include=usersPwd")
    suspend fun getBruker(@Path("brukerNavn") brukerNavn: String): Response<RecordsBruker>
}
