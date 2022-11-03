package com.example.datahentingtest.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datahentingtest.model.*
import com.example.datahentingtest.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {
    private val mutableProveResponse: MutableLiveData<Response<Prove>> = MutableLiveData()
    val mutableAlleProverResponse: MutableLiveData<Response<RecordsKort>> = MutableLiveData()
    val mutableProvenResponse: MutableLiveData<Response<RecordsTest>> = MutableLiveData()
    val mutableBrukerResponse: MutableLiveData<Response<RecordsBruker>> = MutableLiveData()

    fun getProve(verdien: Int, verdien2: String) {
        viewModelScope.launch {
            val responseProve :Response<Prove> = repository.getProve(verdien, verdien2)
            mutableProveResponse.value = responseProve

        }
    }

    fun getAlleProver() {
        viewModelScope.launch{
            val responseAlleProver : Response<RecordsKort> = repository.getAlleProver()
            mutableAlleProverResponse.value = responseAlleProver
        }
    }

    fun getProven(verdier: String) {
        viewModelScope.launch {
            val responseProven : Response<RecordsTest> = repository.getProven(verdier)
            mutableProvenResponse.value = responseProven
        }
    }

    fun getBruker(verdi: String) {
        viewModelScope.launch {
            val responseBruker : Response<RecordsBruker> = repository.getBruker(verdi)
            mutableBrukerResponse.value = responseBruker
        }
    }
}