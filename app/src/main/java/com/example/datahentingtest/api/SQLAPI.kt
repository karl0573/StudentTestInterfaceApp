package com.example.datahentingtest.api
import com.example.datahentingtest.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SQLAPI {
    @GET("BrukerQuiz")
    suspend fun getPost(@Query("filter")filter: String): Response<RecordsPost>

    @GET("users/{usersId}")
    suspend fun getBrukernavn(@Path("usersId") usersId: Int): Response<RecordsBrukernavn>

    @GET("{proveNavn}/{SporsmalNr}")
    suspend fun getProve(@Path("SporsmalNr") SporsmalNr: Int,
                         @Path("proveNavn") proveNavn: String
                         ): Response<Prove>

    @GET("BrukerQuiz")
    suspend fun getAlleProver(): Response<RecordsKort>

    @GET("{proveNavn}")
    suspend fun getProven(@Path("proveNavn") proveNavn: String): Response<RecordsTest>

    @GET("users")
    suspend fun getBruker(
        @Query("filter")filter: String): Response<RecordsBruker>

    //@GET("users?filter=usersUid,eq,tester")
    //suspend fun getBruker(@Query("filter") tester: String): Response<RecordsBruker>
}

