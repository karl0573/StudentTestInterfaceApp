package com.example.datahentingtest

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
import com.example.datahentingtest.model.Bruker
import com.example.datahentingtest.repository.Repository
import com.example.datahentingtest.viewModel.MainViewModel
import com.example.datahentingtest.viewModel.MainViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityLoginBinding
    private lateinit var hamburgerIkon: ActionBarDrawerToggle
    private lateinit var startIntent: Intent
    private var storrelse = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        hamburgerIkon= ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(hamburgerIkon.onOptionsItemSelected(item)) {
            true
        }
        return super.onOptionsItemSelected(item)
    }

    fun loggInn(view: View) {
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.getBruker()
        viewModel.mutableBrukerResponse.observe(this) { response ->
            storrelse = response.body()!!.records.size
            if (storrelse > 0) {

            Toast.makeText(applicationContext, "Brukereren finnes JIPPI", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(applicationContext, "Brukernavn finnes ikke ;((((", Toast.LENGTH_SHORT).show();
        }

            val bruker1 = Bruker(
                response.body()!!.records[0].usersId,
                response.body()!!.records[0].usersName,
                response.body()!!.records[0].usersEmail,
                response.body()!!.records[0].usersUid,
                response.body()!!.records[0].usersPwd
            )


            binding.editTextTextPersonName.setText(bruker1.usersName)
        }

       // binding.editTextTextPersonName.setText(storrelse.toString())



        /**
        if(binding.editTextTextPersonName.text.toString().length == 0) {
        Toast.makeText(applicationContext, "Du må fylle ut brukernavn feltet :DDDD", Toast.LENGTH_SHORT).show();
        }
        else if(binding.editTextTextPassword.text.toString().length == 0) {
        Toast.makeText(applicationContext, "Du må fylle ut passord feltet :DDDD", Toast.LENGTH_SHORT).show();
        }
        else {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        }
         */
    }

}
