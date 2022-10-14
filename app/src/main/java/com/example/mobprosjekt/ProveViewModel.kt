package com.example.mobprosjekt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

enum class STIApiStatus {LOADING,ERROR, DONE}

class ProveViewModel : ViewModel() {

    private val _status = MutableLiveData<STIApiStatus>()
    val status: LiveData<STIApiStatus> = _status
    private val _prover = MutableLiveData<List<Prove>>()
    val prove: LiveData<List<Prove>> = _prover

    init {getAlleProver()}

    private fun getAlleProver(){
        viewModelScope.launch {
        _status.value = STIApiStatus.LOADING
        try{
            _prover.value = STIApi.retrofitService.getAlleProver()
            _status.value = STIApiStatus.DONE
        } catch (e: Exception) {
           _status.value = STIApiStatus.ERROR
           _prover.value = listOf()
           }
        }
    }
}
