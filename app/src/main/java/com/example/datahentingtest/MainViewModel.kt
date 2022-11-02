package com.example.datahentingtest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datahentingtest.model.Post
import com.example.datahentingtest.model.Prove
import com.example.datahentingtest.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    val mutablePostResponse: MutableLiveData<Response<Post>> = MutableLiveData()
    val mutableProveResponse: MutableLiveData<Response<Prove>> = MutableLiveData()

    fun getPost() {
        viewModelScope.launch {
            val responsePost :Response<Post> = repository.getPost()
            mutablePostResponse.value = responsePost

        }
    }
    fun getProve() {
        viewModelScope.launch {
            val responseProve :Response<Prove> = repository.getProve()
            mutableProveResponse.value = responseProve

        }
    }


}