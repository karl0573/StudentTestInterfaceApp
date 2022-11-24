package com.example.datahentingtest

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.graphics.toColor
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import com.example.datahentingtest.databasemappe.Repository
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.datahentingtest.databinding.ActivityMainBinding
import com.example.datahentingtest.kort.KortAdapter
import com.example.datahentingtest.kort.ListeClickListener
import com.example.datahentingtest.databasemappe.MainViewModel
import com.example.datahentingtest.databasemappe.MainViewModelFactory
import com.example.datahentingtest.dataklasser.*

class MainActivity : AppCompatActivity(), ListeClickListener<Kort> {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var hamburgerIkon: ActionBarDrawerToggle
    lateinit var startIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        hamburgerIkon= ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(hamburgerIkon)
        hamburgerIkon.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        hentProfilProver()

        val mainActivity = this
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext,1)
            adapter = KortAdapter(kortListe,mainActivity)
        }
        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.hjemItem -> startIntent = Intent(this, MainActivity::class.java)
                R.id.profilItem -> startIntent = Intent(this, ProfilActivity::class.java)
                R.id.loginItem -> startIntent = Intent(this, LoginActivity::class.java)
            }
            startActivity(startIntent)
            finish()
            true
        }
    }

    override fun onClick(kort: Kort) {
        val intent = Intent(this,ProveActivity::class.java)
        intent.putExtra(KORT_ID,kort.proveNavn)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(hamburgerIkon.onOptionsItemSelected(item)) {
            true
        }
        return super.onOptionsItemSelected(item)
    }

/*
    private fun hentKortData(){
        if(kortListe.size > 0) {
        }
        else {
            val repository = Repository()
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
            viewModel.getAlleProver()
            viewModel.mutableAlleProverResponse.observe(this) { response ->
                if (response.isSuccessful) {
                    //teksten.text = response.body()!!.records[0].proveNavn
                    var i = 0
                    while(i < response.body()!!.records.size) {
                        val kort = Kort(
                            response.body()?.records!![i].brukerId,
                            response.body()?.records!![i].proveNavn
                            )
                            kortListe.add(kort)
                            i++
                    }
                }
            }
        }
    }
 */

    private fun hentProfilProver() {
        if(posterListe.size == 0) {
            val repository = Repository()
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
            val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            var brukerId = sharedPreferences.getInt("INT_KEY", 0)
            val filter = "brukerId,eq,$brukerId";
            viewModel.getPost(filter)
            viewModel.mutablePostResponse.observe(this) { response ->
                if (response.isSuccessful) {
                    for(i in response.body()!!.records.indices) {
                        val post1 = Kort(
                            response.body()?.records!![i].brukerId,
                            response.body()?.records!![i].proveNavn
                        )
                        posterListe.add(post1)
                    }
                }
            }
        }
    }
}