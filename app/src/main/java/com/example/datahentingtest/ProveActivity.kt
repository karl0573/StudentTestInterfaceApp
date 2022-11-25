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
import com.example.datahentingtest.dataklasser.*
import com.example.datahentingtest.databasemappe.Repository
import com.example.datahentingtest.databasemappe.MainViewModel
import com.example.datahentingtest.databasemappe.MainViewModelFactory
import com.example.datahentingtest.databinding.ActivityProveBinding

class ProveActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityProveBinding
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var startIntent: Intent
    private lateinit var riktigSvaret: String
    private lateinit var prøveListe: MutableList<Prove>
    private lateinit var randomSvarListe: MutableList<Svar>
    private var spørsmålNr = 1
    private var antallSporsmal = 0;
    private var indexPos = 0
    private var huskeListe: MutableList<Int> = ArrayList()
    private var brukerSvar: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_prove)
        val kortID = intent.getStringExtra(KORT_ID)
        val kort = kortFraID(kortID!!)
        val proveNavnet = kort!!.proveNavn
        hentProveData(proveNavnet)

        drawerToggle = ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.hjemItem   -> startIntent = Intent(this, MainActivity::class.java)
                R.id.profilItem -> startIntent = Intent(this, ProfilActivity::class.java)
                R.id.loginItem  -> startIntent = Intent(this, LoginActivity::class.java)
            }
            startActivity(startIntent)
            finish()
            true
        }
    }

    private fun kortFraID(kortID: String): Kort? {
        for(kort in kortListe)
            if(kort.proveNavn == kortID)
                return kort
        return null
    }

    private fun getAlleSvar(): MutableList<Svar> {
        val returListe = mutableListOf<Svar>()
        for(i in prøveListe.indices) {
            val svarListe = listOf(prøveListe[i].RiktigSvar, prøveListe[i].Svar2,  prøveListe[i].Svar3, prøveListe[i].Svar4)
            val randomListe = svarListe.shuffled()
            val svar = Svar(
                randomListe[0],
                randomListe[1],
                randomListe[2],
                randomListe[3],
            )
            returListe.add(svar)
        }
        return returListe
    }

    private fun hentProveData(proveNavn: String) {
        prøveListe = mutableListOf<Prove>()
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getProven(proveNavn)
        viewModel.mutableProvenResponse.observe(this) { response ->
            if (response.isSuccessful) {
                for(i in response.body()!!.records.indices) {
                    val prove1 = Prove(
                        response.body()!!.records[i].SpørsmålNr,
                        response.body()!!.records[i].OppgaveTekst,
                        response.body()!!.records[i].RiktigSvar,
                        response.body()!!.records[i].Svar2,
                        response.body()!!.records[i].Svar3,
                        response.body()!!.records[i].Svar4,
                    )
                    prøveListe.add(prove1)
                }
                antallSporsmal = prøveListe.size
                binding.progresjonBar.max = antallSporsmal
                randomSvarListe = getAlleSvar()
                oppdater()
            }
            else {
                Log.d("response", response.errorBody().toString())
            }
        }
    }

    fun nesteSpørsmål(view: View) {
        if(spørsmålNr >= antallSporsmal) {  // Sjekker om det fortsatt er flere spørsmål.Viser resultatskjerm hvis det er tomt
            if(binding.contactgroup.checkedRadioButtonId == -1) {
                Toast.makeText(applicationContext, getString(R.string.fmVelgSvar), Toast.LENGTH_SHORT).show()
            }
            else {
                when (binding.contactgroup.checkedRadioButtonId) {
                    R.id.radio1 -> brukerSvar.add(indexPos, binding.radio1.text.toString())
                    R.id.radio2 -> brukerSvar.add(indexPos, binding.radio2.text.toString())
                    R.id.radio3 -> brukerSvar.add(indexPos, binding.radio3.text.toString())
                    R.id.radio4 -> brukerSvar.add(indexPos, binding.radio4.text.toString())
                }
                var poeng = 0
                for(i in prøveListe.indices) {
                    if(brukerSvar[i] == prøveListe[i].RiktigSvar)
                        poeng++
                }
                binding.apply {
                    contactgroup.visibility = View.GONE
                    antSpm.visibility = View.GONE
                    progresjonBar.visibility = View.GONE
                    knappMeny!!.visibility = View.GONE
                    resultatSkjerm.visibility = View.VISIBLE
                    txtPoengsum.text = getString(R.string.txtPoengsum) + poeng + "/" + antallSporsmal
                }
            }
        } else {
            if(binding.contactgroup.checkedRadioButtonId == -1) {
                Toast.makeText(applicationContext, getString(R.string.fmVelgSvar), Toast.LENGTH_SHORT).show()
            }
            else {
                when (binding.contactgroup.checkedRadioButtonId) {
                    R.id.radio1 -> {
                        brukerSvar.add(indexPos,binding.radio1.text.toString())
                        huskeListe.add(indexPos,1)
                    }
                    R.id.radio2 -> {
                        brukerSvar.add(indexPos,binding.radio2.text.toString())
                        huskeListe.add(indexPos,2)
                    }
                    R.id.radio3 -> {
                        brukerSvar.add(indexPos,binding.radio3.text.toString())
                        huskeListe.add(indexPos,3)
                    }
                    R.id.radio4 -> {
                        brukerSvar.add(indexPos,binding.radio4.text.toString())
                        huskeListe.add(indexPos,4)
                    }
                }
                binding.contactgroup.clearCheck()
                binding.progresjonBar.setProgress(spørsmålNr)
                spørsmålNr += 1
                indexPos += 1
                if(spørsmålNr > 1)
                    binding.forrigeKnapp.visibility = View.VISIBLE
                oppdater()
            }
        }
    }


    fun forrigeSpørsmål(view: View) {
        spørsmålNr  -= 1
        indexPos    -= 1
        binding.progresjonBar.progress = spørsmålNr-1
        if(spørsmålNr == 1)
            binding.forrigeKnapp!!.visibility = View.GONE
        oppdater()
        when(huskeListe[indexPos]) {
            1 -> binding.contactgroup.check(R.id.radio1)
            2 -> binding.contactgroup.check(R.id.radio2)
            3 -> binding.contactgroup.check(R.id.radio3)
            4 -> binding.contactgroup.check(R.id.radio4)
        }
    }

    fun oppdater() {
        riktigSvaret = prøveListe[indexPos].RiktigSvar
        binding.radiogruppeTekst.text = prøveListe[indexPos].OppgaveTekst
        binding.radio1.text = randomSvarListe[indexPos].svar1
        binding.radio2.text = randomSvarListe[indexPos].svar2
        binding.radio3.text = randomSvarListe[indexPos].svar3
        binding.radio4.text = randomSvarListe[indexPos].svar4
        binding.antSpm.text = getString(R.string.txtSpmNr) + spørsmålNr + "/" + antallSporsmal
    }

    fun avsluttProve(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(drawerToggle.onOptionsItemSelected(item)) true
        return super.onOptionsItemSelected(item)
    }
}