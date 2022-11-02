package com.example.datahentingtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.datahentingtest.databinding.ActivityProveBinding
import com.example.datahentingtest.model.KORT_ID
import com.example.datahentingtest.model.Kort
import com.example.datahentingtest.model.Prove
import com.example.datahentingtest.model.kortListe
import com.example.datahentingtest.model.*
import com.example.datahentingtest.repository.Repository
import com.example.datahentingtest.viewModel.MainViewModel
import com.example.datahentingtest.viewModel.MainViewModelFactory

class ProveActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityProveBinding
    lateinit var hamburgerIkon: ActionBarDrawerToggle
    lateinit var startIntent: Intent
    lateinit var riktigSvaret: String
    lateinit var prøveListe: MutableList<Prove>
    var spørsmålNr = 1
    var antallSporsmal = 0
    var poengsum = 0
    var indexPos = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_prove)
        val kortID = intent.getStringExtra(KORT_ID)
        val kort = kortFraID(kortID!!)
        var proveNavnet = kort!!.proveNavn
        hentProveData(proveNavnet)

        hamburgerIkon = ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(hamburgerIkon)
        hamburgerIkon.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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

    private fun hentProveData(proveNavn: String) {
        prøveListe = mutableListOf<Prove>()
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getProven(proveNavn)
        viewModel.mutableProvenResponse.observe(this) { response ->
            if (response.isSuccessful) {
                var i = 0
                while(i < response.body()!!.records.size) {
                    val prove1 = Prove(
                        response.body()!!.records[i].SpørsmålNr,
                        response.body()!!.records[i].OppgaveTekst,
                        response.body()!!.records[i].RiktigSvar,
                        response.body()!!.records[i].Svar2,
                        response.body()!!.records[i].Svar3,
                        response.body()!!.records[i].Svar4,
                    )
                    prøveListe.add(prove1)
                    i++
                }
                antallSporsmal = prøveListe.size
                binding.progresjonBar.max = antallSporsmal
                oppdater()
            }
            else {
                Log.d("response", response.errorBody().toString())
            }
        }
    }

    private fun kortFraID(kortID: String): Kort? {
        for(kort in kortListe) {
            if(kort.proveNavn == kortID)
                return kort
        }
        return null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(hamburgerIkon.onOptionsItemSelected(item)) {
            true
        }
        return super.onOptionsItemSelected(item)
    }

    fun nesteSpørsmål(view: View) {
        if(spørsmålNr >= antallSporsmal) {  // Sjekker om det fortsatt er flere spørsmål.Viser resultatskjerm hvis det er tomt
            binding.txtPoengsum!!.text = "Poengsum: $poengsum/$antallSporsmal"
            binding.contactgroup.visibility = View.GONE
            binding.antSpm.visibility = View.GONE
            binding.progresjonBar.visibility = View.GONE
            binding.button2.visibility = View.GONE
            binding.resultatSkjerm.visibility = View.VISIBLE
        }
        else {
            if(binding.contactgroup.checkedRadioButtonId == -1) {
                Toast.makeText(applicationContext, "Du må velge ett svaralternativ", Toast.LENGTH_SHORT).show();
            }
            else {
                when (binding.contactgroup.checkedRadioButtonId) {
                    R.id.radio1 -> {
                        if (binding.radio1.text == riktigSvaret)
                            poengsum += 1
                    }
                    R.id.radio2 -> {
                        if (binding.radio2.text == riktigSvaret)
                            poengsum += 1
                    }
                    R.id.radio3 -> {
                        if (binding.radio3.text == riktigSvaret)
                            poengsum += 1
                    }
                    R.id.radio4 -> {
                        if (binding.radio4.text == riktigSvaret)
                            poengsum += 1
                    }
                }
                binding.contactgroup.clearCheck()
                binding.progresjonBar.setProgress(spørsmålNr)
                indexPos += 1
                spørsmålNr += 1
                oppdater()
            }
        }
    }

    fun oppdater() {
        binding.antSpm.text = "Spørsmål: $spørsmålNr/$antallSporsmal"
        riktigSvaret = prøveListe.get(indexPos).RiktigSvar
        val svarListe = listOf(prøveListe.get(indexPos).RiktigSvar, prøveListe.get(indexPos).Svar2, prøveListe.get(indexPos).Svar3, prøveListe.get(indexPos).Svar4)
        var randomListe = svarListe.shuffled()
        binding.radiogruppeTekst!!.text = prøveListe.get(indexPos).OppgaveTekst
        binding.radio1.text = randomListe[0]
        binding.radio2.text = randomListe[1]
        binding.radio3.text = randomListe[2]
        binding.radio4.text = randomListe[3]
    }

    fun avsluttProve(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}