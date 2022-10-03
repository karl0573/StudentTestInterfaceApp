package com.example.mobprosjekt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.mobprosjekt.databinding.ActivityProfilBinding

class ProfilActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfilBinding
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            toggle= ActionBarDrawerToggle(this@ProfilActivity,drawerLayout,R.string.open,R.string.close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            // to make the Navigation drawer icon always appear on the action bar
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            navView3.setNavigationItemSelectedListener {
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

}