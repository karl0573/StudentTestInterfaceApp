package com.example.datahentingtest.kort

import androidx.recyclerview.widget.RecyclerView
import com.example.datahentingtest.R
import com.example.datahentingtest.databinding.ProfilCardLayoutBinding
import com.example.datahentingtest.dataklasser.Kort

class PostViewHolder(
    private val cardCellBinding: ProfilCardLayoutBinding,
    private val clickListener: ListeClickListener<Kort>) : RecyclerView.ViewHolder(cardCellBinding.root) {

    fun bindProve(kort: Kort) {
        cardCellBinding.cardImage.setImageResource(R.drawable.blyant)
        cardCellBinding.cardTittel.text = kort.proveNavn
        cardCellBinding.cardBruker.text = "Bruker ID: " + kort.brukerId.toString()
        cardCellBinding.btSlett.setOnClickListener{
            clickListener.onClick(kort)
        }
    }
}