package com.example.datahentingtest.kort

import androidx.recyclerview.widget.RecyclerView
import com.example.datahentingtest.model.Kort
import com.example.mobprosjekt.R
import com.example.mobprosjekt.databinding.ProveCardLayoutBinding

class KortViewHolder(
    private val cardCellBinding: ProveCardLayoutBinding,
    private val clickListener: KortClickListener
 ) : RecyclerView.ViewHolder(cardCellBinding.root) {

    fun bindProve(prove: Kort) {
        cardCellBinding.cardImage.setImageResource(R.drawable.blyant)
        cardCellBinding.cardTittel.text = prove.proveNavn
        cardCellBinding.cardBruker.text = "Bruker ID: ${prove.brukerId.toString()}"
        cardCellBinding.cardView.setOnClickListener {
            clickListener.onClick(prove)
        }
    }
}