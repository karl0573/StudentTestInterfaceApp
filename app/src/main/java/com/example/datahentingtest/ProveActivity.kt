package com.example.datahentingtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.datahentingtest.R
import com.example.datahentingtest.databinding.ActivityProveBinding
import com.example.datahentingtest.model.Post
import com.example.datahentingtest.model.Prove
import com.example.datahentingtest.model.postListe
import com.example.datahentingtest.model.proveListe
import com.example.datahentingtest.repository.Repository

class ProveActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityProveBinding
    lateinit var hamburgerIkon: ActionBarDrawerToggle // Hamburger ikon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_prove)
        hentProveData()
        hamburgerIkon = ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(hamburgerIkon)
        hamburgerIkon.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.hjemItem->{ velgSide(binding.drawerLayout,1) }
                    R.id.profilItem->{ velgSide(binding.drawerLayout,2) }
                    R.id.loginItem->{ velgSide(binding.drawerLayout,3) }
                }
                true
            }

    }
    fun hentProveData(){

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getProve()
        viewModel.mutableProveResponse.observe(this, Observer { response ->
            if(response.isSuccessful){
                val prove1 = Prove(
                    response.body()?.SpørsmålNr!!,
                    response.body()?.OppgaveTekst!!,
                    response.body()?.RiktigSvar!!,
                    response.body()?.Svar2!!,
                    response.body()?.Svar3!!,
                    response.body()?.Svar4!!,
                    response.body()?.Brukernavn!!
                )
                //proveListe.add(prove1)

                binding.radiogruppeTekst!!.text = prove1.OppgaveTekst
                binding.radio1.text = prove1.RiktigSvar
                binding.radio2.text = prove1.Svar2
                binding.radio3.text = prove1.Svar3
                binding.radio4.text = prove1.Svar4
            } else {
                Log.d("response", response.errorBody().toString())
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(hamburgerIkon.onOptionsItemSelected(item)) {
            true
        }
        return super.onOptionsItemSelected(item)
    }

    fun velgSide(view: View, tall: Int) {
        when(tall) {
            1 -> {
                val startIntent = Intent(this, MainActivity::class.java)
                startActivity(startIntent)
            }
            2 -> {
                val startIntent = Intent(this, ProfilActivity::class.java)
                startActivity(startIntent)
            }
            3 -> {
                val startIntent = Intent(this, LoginActivity::class.java)
                startActivity(startIntent)
            }
        }
    }

    fun visResultat(view: View) {
        binding.contactgroup.visibility = View.GONE
        binding.antSpm.visibility = View.GONE
        binding.resultatSkjerm.visibility = View.VISIBLE
        binding.progresjonBar.visibility = View.GONE
        binding.button2.visibility = View.GONE
    }

    fun avsluttProve(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}