package com.example.datahentingtest

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
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
    private lateinit var drawerToggle: ActionBarDrawerToggle
    lateinit var startIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        drawerToggle= ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        hentProfilProver()

        binding.recyclerView.layoutManager = GridLayoutManager(applicationContext,1)
        binding.recyclerView.adapter = KortAdapter(kortListe,this)

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
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(drawerToggle.onOptionsItemSelected(item)) true
        return super.onOptionsItemSelected(item)
    }

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