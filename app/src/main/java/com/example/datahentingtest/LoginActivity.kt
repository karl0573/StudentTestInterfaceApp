package com.example.datahentingtest

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.datahentingtest.databinding.ActivityLoginBinding
import com.example.datahentingtest.databasemappe.Repository
import com.example.datahentingtest.databasemappe.MainViewModel
import com.example.datahentingtest.databasemappe.MainViewModelFactory
import com.example.datahentingtest.dataklasser.*

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModel2: MainViewModel
    private lateinit var viewModel3: MainViewModel
    lateinit var binding: ActivityLoginBinding
    private lateinit var hamburgerIkon: ActionBarDrawerToggle
    private lateinit var startIntent: Intent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        hamburgerIkon= ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(hamburgerIkon)
        hamburgerIkon.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        hentKortData()
        hentProfilProver()
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
/*
    private fun lagreData() {
       // val brukernavnText = binding.editTextTextPersonName.text.toString()
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("INT_KEY", brukernavnText).apply()
    } */
/*
    private fun hentData() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedInt = sharedPreferences.getInt("INT_KEY", null)
    }
*/
    private fun hentKortData(){
        if(kortListe.size > 0) {
        }
        else {
            val repository = Repository()
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel2 = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
            viewModel2.getAlleProver()
            viewModel2.mutableAlleProverResponse.observe(this) { response ->
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

    private fun hentProfilProver() {
        if(posterListe.size > 0) {
        }
        else {
            val repository = Repository()
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
             val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        var brukerId = sharedPreferences.getInt("INT_KEY", 0)

            val filter = "brukerId,eq,$brukerId";
            viewModel.getPost(filter)
            viewModel.mutablePostResponse.observe(this) { response ->
                if (response.isSuccessful) {
                    //teksten.text = response.body()!!.records[0].proveNavn
                    var i = 0
                    while(i < response.body()!!.records.size) {
                        val post1 = Post(
                            response.body()?.records!![i].brukerId,
                            response.body()?.records!![i].proveNavn
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

    fun loggInn(view: View) {
        if(binding.editTextTextPersonName.text.toString().isEmpty()) {
            binding.txtFeilmelding?.text = "Du må fylle ut brukernavnfeltet"
            //Toast.makeText(applicationContext, "Du må fylle ut brukernavn feltet :DDDD", Toast.LENGTH_SHORT).show();
        }
        else if(binding.editTextTextPassword.text.toString().isEmpty()) {
            binding.txtFeilmelding?.text = "Du må fylle ut passordfeltet"
           // Toast.makeText(applicationContext, "Du må fylle ut passord feltet :DDDD", Toast.LENGTH_SHORT).show();
        }
        else {
            val repository = Repository()
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel3 = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
            // viewModel.getBruker(/*"tester"*/)
            var brukerId = binding.editTextTextPersonName.text
            // var brukerId = "vebteo"
            val filter = "usersUid,eq,$brukerId";
            viewModel3.getBruker(filter)
            viewModel3.mutableBrukerResponse.observe(this) { response ->
                var size = response.body()!!.records.size
                if(size == 0) {
                    binding.txtFeilmelding?.text = "Brukernavnet finnes ikke"
                 //   Toast.makeText(applicationContext, "Brukernavn finnes ikke ;((((", Toast.LENGTH_SHORT).show();
                } else {
                    var bruker1 = Bruker(
                        response.body()!!.records[0].usersId,
                        response.body()!!.records[0].usersName,
                        response.body()!!.records[0].usersEmail,
                        response.body()!!.records[0].usersUid,
                        response.body()!!.records[0].usersPwd
                    )
                    if(size > 0 && bruker1.usersPwd == binding.editTextTextPassword.text.toString()) {
                   //     Toast.makeText(applicationContext, "Brukereren finnes JIPPI", Toast.LENGTH_SHORT).show();
                   // val brukernavnText = binding.editTextTextPersonName.text.toString()
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("INT_KEY", response.body()!!.records[0].usersId).apply()

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else {
                        binding.txtFeilmelding?.text = "Feil passord"
                     //   Toast.makeText(applicationContext, "Feil passord", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
