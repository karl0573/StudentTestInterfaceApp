package com.example.datahentingtest.kort

import androidx.recyclerview.widget.RecyclerView
import com.example.datahentingtest.R
import com.example.datahentingtest.databinding.ProfilCardLayoutBinding
import com.example.datahentingtest.model.Post

class PostViewHolder(
    private val cardCellBinding: ProfilCardLayoutBinding,
    private val clickListener: PostClickListener
) : RecyclerView.ViewHolder(cardCellBinding.root) {

    fun bindProve(prove: Post) {
        cardCellBinding.cardImage.setImageResource(R.drawable.blyant)
        cardCellBinding.cardTittel.text = prove.proveNavn
        cardCellBinding.cardBruker.text = "Bruker ID: ${prove.brukerId.toString()}"
        cardCellBinding.cardView.setOnClickListener {
            clickListener.onClick(prove)
        }
    }
}