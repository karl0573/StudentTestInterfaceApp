package com.example.datahentingtest.databasemappe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(
    private val repository: Repository
    ): ViewModelProvider.Factory {
    override fun <V : ViewModel> create(modelClass: Class<V>): V {
        return MainViewModel(repository) as V
    }
}