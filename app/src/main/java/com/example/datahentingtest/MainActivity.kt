package com.example.datahentingtest

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
import com.example.datahentingtest.dataklasser.kortListe
import com.example.datahentingtest.kort.KortAdapter
import com.example.datahentingtest.kort.ListeClickListener
import com.example.datahentingtest.dataklasser.KORT_ID
import com.example.datahentingtest.dataklasser.Kort
import com.example.datahentingtest.databasemappe.MainViewModel
import com.example.datahentingtest.databasemappe.MainViewModelFactory

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
        //hentKortData()
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
/**
    fun startProve(view: View) {
        val startIntent = Intent(this, ProveActivity::class.java)
        startActivity(startIntent)
    }
    */
}