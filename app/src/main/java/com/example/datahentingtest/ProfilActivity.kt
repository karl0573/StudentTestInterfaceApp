package com.example.datahentingtest

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.datahentingtest.databinding.ActivityProfilBinding
import com.example.datahentingtest.kort.PostAdapter
import com.example.datahentingtest.model.*
import com.example.datahentingtest.repository.Repository
import com.example.datahentingtest.viewModel.MainViewModel
import com.example.datahentingtest.viewModel.MainViewModelFactory

class ProfilActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfilBinding
    private lateinit var hamburgerIkon: ActionBarDrawerToggle
    private lateinit var startIntent: Intent
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModel2: MainViewModel
    lateinit var currentNavn: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profil)
        hamburgerIkon= ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(hamburgerIkon)
        hamburgerIkon.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        hentProfilProver()








        binding.recyclerView?.apply {
            layoutManager = GridLayoutManager(applicationContext, 1)
            adapter = PostAdapter(posterListe)
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

        val repository2 = Repository()
        val viewModelFactory2 = MainViewModelFactory(repository2)
        viewModel2 = ViewModelProvider(this, viewModelFactory2)[MainViewModel::class.java]
        viewModel2.getBrukernavn(3)
        viewModel2.mutableBrukernavnResponse.observe(this) { response ->
            if (response.isSuccessful) {
                 binding.textView2.text = response.body()!!.usersUid
                binding.editText?.setText(response.body()!!.usersUid)
                currentNavn = response.body()!!.usersUid

            }
        }


    }

    @SuppressLint("SuspiciousIndentation")
    private fun hentProfilProver() {
        
        if(posterListe.size > 0) {
        }
        else {

            val repository = Repository()
                val viewModelFactory = MainViewModelFactory(repository)
                viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
                var brukerId = 7
                val filter = "brukerId,eq,$brukerId";
                viewModel.getPost(filter)

                viewModel.mutablePostResponse.observe(this) { response ->
                    if (response.isSuccessful) {
                        //teksten.text = response.body()!!.records[0].proveNavn
                        var i = 0
                        while(i < response.body()!!.records.size) {
                            val post1 = Post(
                                response.body()?.records!![i].brukerId!!,
                                response.body()?.records!![i].proveNavn!!
                                )
                                posterListe.add(post1)
                                i++
                        }
                    }
                }
        } 
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(hamburgerIkon.onOptionsItemSelected(item)) {
            true
        }
        return super.onOptionsItemSelected(item)
    }

    fun slettProve(view: View) {
        //posterListe.remove(posterListe.get(0))

         val repository2 = Repository()
        val viewModelFactory2 = MainViewModelFactory(repository2)
        viewModel2 = ViewModelProvider(this, viewModelFactory2)[MainViewModel::class.java]
        viewModel2.slettProve("slett1")
        viewModel2.mutableSlettProveResponse.observe(this) { response ->
            if (response.isSuccessful) {
                 Toast.makeText(applicationContext, "Tror den ble slettet?", Toast.LENGTH_SHORT).show();
            }
        }

    }

    fun endreBrukernavn(view: View) {
        binding.btBekreft?.visibility = View.VISIBLE
        binding.btAvbryt?.visibility = View.VISIBLE
        binding.btEndre?.visibility = View.GONE
         binding.textView2.visibility = View.GONE
        binding.editText!!.visibility = View.VISIBLE
        /*
        binding.textView2.visibility = View.GONE
        binding.editText!!.visibility = View.VISIBLE
        if(currentNavn != binding.editText!!.text.toString()) {
            // Kode for oppdater brukernavn
            // JSON-kall?
            // sett nytt navn som text-verdi i textfield
            // hide editText
            // vis textfield
        } else if(binding.editText!!.text.toString().isEmpty()) {
            // Brukernavn finnes allerede eller brukernavn er 0 bokstaver
            Toast.makeText(applicationContext, "Brukernavnet kan ikke være 0 lengde", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(applicationContext, "Brukernavnet er allerede i bruk!", Toast.LENGTH_SHORT).show();
        }
        */
    }

    fun avbrytKnapp(view: View) {
         binding.btBekreft?.visibility = View.GONE
        binding.btAvbryt?.visibility = View.GONE
        binding.btEndre?.visibility = View.VISIBLE
         binding.textView2.visibility = View.VISIBLE
        binding.editText!!.visibility = View.GONE

    }

    fun bekreftKnapp(view: View) {
          binding.btBekreft?.visibility = View.GONE
        binding.btAvbryt?.visibility = View.GONE
        binding.btEndre?.visibility = View.VISIBLE
         binding.textView2.visibility = View.VISIBLE
        binding.editText!!.visibility = View.GONE

    }
    
}