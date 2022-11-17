package com.example.datahentingtest.databasemappe

import com.example.datahentingtest.dataklasser.*
import retrofit2.Response

class Repository {
    suspend fun getPost(verdi: String): Response<RecordsPost> {
        return RetrofitInstance.api.getPost(verdi)
    }

    suspend fun getProve(verdi: Int, verdi2: String): Response<Prove> {
        return RetrofitInstance.api.getProve(verdi, verdi2)
    }

    suspend fun getAlleProver(): Response<RecordsKort> {
        return RetrofitInstance.api.getAlleProver()
    }

    suspend fun getProven(verdier: String): Response<RecordsTest> {
        return RetrofitInstance.api.getProven(verdier)
    }

    /*
    suspend fun getBruker(Uid: String): Response<RecordsBruker> {
        return RetrofitInstance.api.getBruker(Uid)
    }
    */
    suspend fun getBruker(Uid: String): Response<RecordsBruker> {
        return RetrofitInstance.api.getBruker(Uid)
    }

    suspend fun getBrukernavn(usersId: Int): Response<Bruker> {
        return RetrofitInstance.api.getBrukernavn(usersId)
    }

    suspend fun slettProve(proveNavn: String) {
        return RetrofitInstance.api.slettProve(proveNavn)
    }    
    
    suspend fun endreBrukernavn(usersUid: Int, brukers: Bruker) {
        return RetrofitInstance.api.endreBrukernavn(usersUid, brukers)
    }


}