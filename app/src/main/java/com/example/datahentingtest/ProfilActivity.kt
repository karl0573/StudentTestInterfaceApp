package com.example.datahentingtest

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
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

class ProfilActivity : AppCompatActivity(), ListeClickListener<Kort> {
    private lateinit var binding: com.example.datahentingtest.databinding.ActivityProfilBinding
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var startIntent: Intent
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModel2: MainViewModel
    private lateinit var viewModel3: MainViewModel
    private lateinit var currentNavn: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profil)
        drawerToggle= ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.recyclerView.layoutManager = GridLayoutManager(applicationContext, 1)
        binding.recyclerView.adapter = PostAdapter(posterListe,this)

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

    private fun hentBrukernavn() {
        val repository2 = Repository()
        val viewModelFactory2 = MainViewModelFactory(repository2)
        viewModel2 = ViewModelProvider(this, viewModelFactory2)[MainViewModel::class.java]
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedInt = sharedPreferences.getInt("INT_KEY", 0)
        viewModel2.getBrukernavn(savedInt)
        viewModel2.mutableBrukernavnResponse.observe(this) { response ->
            if (response.isSuccessful) {
                binding.textView2.text = response.body()!!.usersUid
                binding.editText.setText(response.body()!!.usersUid)
                currentNavn = response.body()!!.usersUid
            }
        }
    }

    fun endreBrukernavn(view: View) {
        binding.apply {
            btEndre.visibility = View.GONE
            textView2.visibility = View.GONE
            btBekreft.visibility = View.VISIBLE
            btAvbryt.visibility = View.VISIBLE
            editText.visibility = View.VISIBLE
        }
    }

    fun avbrytKnapp(view: View) {
        updateView()
        binding.editText.setText(currentNavn)
        view.fjernTastatur()
    }

    fun bekreftKnapp(view: View) {
        if(binding.editText.text.toString().isEmpty()) {
            binding.txtProfilFeilmelding.text = getString(R.string.fmNullTegn)
        } else {
            val repository = Repository()
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
            val brukerId = binding.editText.text.toString()
            val filter = "usersUid,eq,$brukerId";
            viewModel.getBruker(filter)
            viewModel.mutableBrukerResponse.observe(this) { response ->
                var size = response.body()!!.records.size
                 if (size >  0) {
                    binding.txtProfilFeilmelding.text = getString(R.string.fmNavnTatt)
                }
                else {
                    updateView()
                    view.fjernTastatur()
                    binding.editText.setText(binding.editText.text.toString())
                    binding.textView2.text = binding.editText.text.toString()
                    binding.txtProfilFeilmelding.visibility = View.GONE
                    val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                    var brukerId = sharedPreferences.getInt("INT_KEY", 0)
                    var brukeren = Bruker(
                        brukerId,
                        "tester",
                        "tester@gmail.com",
                        binding.editText.text.toString(),
                        "tester"
                    )
                    val repository2 = Repository()
                    val viewModelFactory2 = MainViewModelFactory(repository2)
                    viewModel3 = ViewModelProvider(this, viewModelFactory2)[MainViewModel::class.java]
                    viewModel3.endreBrukernavn(brukerId,brukeren)
                }
            }
        }
    }

    private fun updateView() {
        binding.apply {
            btBekreft.visibility = View.GONE
            btAvbryt.visibility = View.GONE
            editText.visibility = View.GONE
            btEndre.visibility = View.VISIBLE
            textView2.visibility = View.VISIBLE
            txtProfilFeilmelding.text = ""
        }
    }

    override fun onClick(post: Kort) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage(getString(R.string.abBekreft))
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setPositiveButton(getString(R.string.abJa)) { a, b ->
            var hent = posterListe.indexOf(post)
            var navn = post.proveNavn
            posterListe.removeAt(hent)
            binding.recyclerView.adapter?.notifyItemRemoved(hent)
            val repository3 = Repository()
            val viewModelFactory3 = MainViewModelFactory(repository3)
            viewModel3 = ViewModelProvider(this, viewModelFactory3)[MainViewModel::class.java]
            viewModel3.slettProve(navn)
        }
        alertDialogBuilder.setNegativeButton(getString(R.string.abNei)) { a, b -> }
        val alert = alertDialogBuilder.create()
        alert.show()
    }

    private fun View.fjernTastatur() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(drawerToggle.onOptionsItemSelected(item)) true
        return super.onOptionsItemSelected(item)
    }
}