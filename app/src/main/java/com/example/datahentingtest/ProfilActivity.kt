package com.example.datahentingtest

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.datahentingtest.kort.PostAdapter
import com.example.datahentingtest.kort.ListeClickListener
import com.example.datahentingtest.dataklasser.*
import com.example.datahentingtest.databasemappe.Repository
import com.example.datahentingtest.databasemappe.MainViewModel
import com.example.datahentingtest.databasemappe.MainViewModelFactory
import com.example.datahentingtest.databinding.ActivityProfilBinding



class ProfilActivity : AppCompatActivity(), ListeClickListener<Post> {
    lateinit var binding: com.example.datahentingtest.databinding.ActivityProfilBinding
    private lateinit var hamburgerIkon: ActionBarDrawerToggle
    private lateinit var startIntent: Intent
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModel2: MainViewModel
    private lateinit var viewModel3: MainViewModel
    lateinit var currentNavn: String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profil)
        hamburgerIkon= ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(hamburgerIkon)
        hamburgerIkon.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
       // hentProfilProver()
        val profilActivity = this
        binding.recyclerView?.apply {
            layoutManager = GridLayoutManager(applicationContext, 1)
            adapter = PostAdapter(posterListe,profilActivity)
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
        hentBrukernavn()
    }

    fun hentBrukernavn() {
        val repository2 = Repository()
        val viewModelFactory2 = MainViewModelFactory(repository2)
        viewModel2 = ViewModelProvider(this, viewModelFactory2)[MainViewModel::class.java]
        viewModel2.getBrukernavn(19)
        viewModel2.mutableBrukernavnResponse.observe(this) { response ->
            if (response.isSuccessful) {
                binding.textView2.text = response.body()!!.usersUid
                binding.editText?.setText(response.body()!!.usersUid)
                currentNavn = response.body()!!.usersUid
            }
        }
    }

/*
    private fun hentProfilProver() {
        if(posterListe.size > 0) {
        }
        else {
            val repository = Repository()
                val viewModelFactory = MainViewModelFactory(repository)
                viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
                var brukerId = 19
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
    } */

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(hamburgerIkon.onOptionsItemSelected(item)) {
            true
        }
        return super.onOptionsItemSelected(item)
    }
/*
    fun slettProve(view: View) {
    } */

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
        binding.editText?.setText(currentNavn)
        binding.txtProfilFeilmelding!!.text = ""
        view.fjernTastatur()
    }

    fun bekreftKnapp(view: View) {
        if(binding.editText!!.text.toString().length == 0) {
            binding.txtProfilFeilmelding!!.text = "Brukernavn kan ikke bestå av 0 bokstaver"
        } else {
            val repository = Repository()
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
            var brukerId = binding.editText!!.text.toString()
            val filter = "usersUid,eq," + brukerId;
            viewModel.getBruker(filter)
            viewModel.mutableBrukerResponse.observe(this) { response ->
                var sizen = response.body()!!.records.size
                 if (sizen >  0) {
                    binding.txtProfilFeilmelding!!.text = "Brukernavn er allerede tatt"
                }
                else {
                    binding.btBekreft?.visibility = View.GONE
                    binding.btAvbryt?.visibility = View.GONE
                    binding.btEndre?.visibility = View.VISIBLE
                    binding.textView2.visibility = View.VISIBLE
                    binding.editText!!.visibility = View.GONE
                    view.fjernTastatur()
                    binding.editText?.setText(binding.editText!!.text.toString())
                    binding.textView2.text = binding.editText!!.text.toString()
                    binding.txtProfilFeilmelding!!.text = ""

                    var brukeren = Bruker(
                        19,
                        "testing",
                        "sigvedankel2@gmail.com",
                        binding.editText!!.text.toString(),
                        "testing"
                    )
                /*
                    val repository2 = Repository()
                    val viewModelFactory2 = MainViewModelFactory(repository2)
                    viewModel3 = ViewModelProvider(this, viewModelFactory2)[MainViewModel::class.java]
                    viewModel3.endreBrukernavn(19,brukeren) */
                }
            }
        }
    }

    fun View.fjernTastatur() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onClick(post: Post) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Er du sikker på at du vil slette prøven?")
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setPositiveButton("Ja") { a, b ->
            var hent = posterListe.indexOf(post)
            var navn = post.proveNavn
            posterListe.removeAt(hent)
            binding.recyclerView?.adapter?.notifyItemRemoved(hent)
/*
            val repository3 = Repository()
            val viewModelFactory3 = MainViewModelFactory(repository3)
            viewModel3 = ViewModelProvider(this, viewModelFactory3)[MainViewModel::class.java]
            viewModel3.slettProve(navn)
 */
        }
        alertDialogBuilder.setNegativeButton("Nei") { a, b ->
        }
        val alert = alertDialogBuilder.create()
        alert.show()
    }
}