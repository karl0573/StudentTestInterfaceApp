package com.example.mobprosjekt

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


private const val BASE_URL ="http://studenttestinterface.com/api.php/records/"
private val moshi = Moshi.Builder()
    .add (KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface STIAPIService {
    @GET("BrukerQuiz")
    suspend fun getAlleProver(): List<Prove>
    @GET("BrukerQuiz/{proveNavn}")
    suspend fun getProve(@Path("proveNavn") tittel:String): Prove
}

object STIApi {
    val retrofitService: STIAPIService by lazy {
    retrofit.create(STIAPIService::class.java)
    }
}