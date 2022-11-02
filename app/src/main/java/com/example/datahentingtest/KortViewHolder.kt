package com.example.datahentingtest

import android.icu.text.AlphabeticIndex
import androidx.recyclerview.widget.RecyclerView
import com.example.datahentingtest.model.Post
import com.example.datahentingtest.R
import com.example.datahentingtest.databinding.ProveCardLayoutBinding

class KortViewHolder(
    private val cardCellBinding: ProveCardLayoutBinding
) : RecyclerView.ViewHolder(cardCellBinding.root) {

    fun bindProve(prove: Post) {
        cardCellBinding.cardImage.setImageResource(R.drawable.blyant)
        cardCellBinding.cardTittel.text = prove.proveNavn
        cardCellBinding.cardBruker.text = prove.brukerId.toString()

    }
}