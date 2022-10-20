package com.example.mobprosjekt

import androidx.recyclerview.widget.RecyclerView
import com.example.mobprosjekt.databinding.ProveCardLayoutBinding

class KortViewHolder(
    private val cardCellBinding: ProveCardLayoutBinding
) : RecyclerView.ViewHolder(cardCellBinding.root) {

    fun bindProve(prove: Prove) {

        cardCellBinding.cardTittel.text = prove.proveNavn
        cardCellBinding.cardBruker.text = prove.brukerId.toString()

    }
}