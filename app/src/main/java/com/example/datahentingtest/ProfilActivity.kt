package com.example.datahentingtest

import android.content.Intent
import android.os.Build.VERSION_CODES.P
import android.os.Build.VERSION_CODES.S
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.datahentingtest.databinding.ActivityProfilBinding
import com.example.datahentingtest.kort.PostAdapter
import com.example.datahentingtest.kort.PostClickListener
import com.example.datahentingtest.model.*
import com.example.datahentingtest.repository.Repository
import com.example.datahentingtest.viewModel.MainViewModel
import com.example.datahentingtest.viewModel.MainViewModelFactory

class ProfilActivity : AppCompatActivity(), PostClickListener {
    lateinit var binding: ActivityProfilBinding
    private lateinit var hamburgerIkon: ActionBarDrawerToggle
    private lateinit var startIntent: Intent
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profil)
        hamburgerIkon= ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(hamburgerIkon)
        hamburgerIkon.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        hentProfilProver()

        val profilActivity = this
        binding.recyclerView?.apply {
            layoutManager = GridLayoutManager(applicationContext, 1)
            adapter = PostAdapter(posterListe, profilActivity)
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

    private fun hentProfilProver() {
        /*
         if(kortListe.size > 0*) {
        }
        else {
        } */

        val repository = Repository()
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
            viewModel.getPost()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(hamburgerIkon.onOptionsItemSelected(item)) {
            true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(Post: Post) {
        TODO("Not yet implemented")
    }

}