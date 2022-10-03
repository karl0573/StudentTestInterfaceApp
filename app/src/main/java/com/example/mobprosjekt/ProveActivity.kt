package com.example.mobprosjekt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import com.example.mobprosjekt.databinding.ActivityMainBinding
import com.example.mobprosjekt.databinding.ActivityProveBinding

class ProveActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProveBinding
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_prove)

        binding.apply {
            toggle = ActionBarDrawerToggle(this@ProveActivity,drawerLayout,R.string.open,R.string.close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            // to make the Navigation drawer icon always appear on the action bar
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            navView2.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.hjemItem->{ velgSide(drawerLayout,1) }
                    R.id.profilItem->{ velgSide(drawerLayout,2) }
                    R.id.loginItem->{ velgSide(drawerLayout,3) }
                }
                true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
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