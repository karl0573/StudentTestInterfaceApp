package com.example.datahentingtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.datahentingtest.viewModel.MainViewModel

class TestActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
/**
        val teksten : TextView = findViewById(R.id.testerTekstingen)
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getStorrelse("Naruto")
        viewModel.mutableStorrelseResponse.observe(this) { response ->
            if (response.isSuccessful) {
               teksten.text = response.body()!!.records.size.toString()
                Log.d("Response", response.body()!!.records.size.toString())
            }
        }
        */
    }
}