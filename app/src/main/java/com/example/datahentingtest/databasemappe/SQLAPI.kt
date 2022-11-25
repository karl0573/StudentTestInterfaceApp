package com.example.datahentingtest.databasemappe
import com.example.datahentingtest.dataklasser.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface SQLAPI {
    @GET("BrukerQuiz")
    suspend fun getPost(@Query("filter")filter: String): Response<RecordsPost>

    @GET("users/{usersId}")
    suspend fun getBrukernavn(@Path("usersId") usersId: Int): Response<Bruker>

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

    @DELETE("BrukerQuiz/{proveNavn}")
    suspend fun slettProve(@Path("proveNavn") proveNavn: String)

    @PUT("users/{usersId}")
    suspend fun endreBrukernavn(@Path("usersId") usersId: Int,
                                @Body brukers: Bruker)
}

