package com.example.datahentingtest.kort

import androidx.recyclerview.widget.RecyclerView
import com.example.datahentingtest.R
import com.example.datahentingtest.databinding.ProfilCardLayoutBinding
import com.example.datahentingtest.dataklasser.Post

class PostViewHolder(
    private val cardCellBinding: ProfilCardLayoutBinding,
    private val clickListener: ListeClickListener<Post>

) : RecyclerView.ViewHolder(cardCellBinding.root) {

    fun bindProve(post: Post) {
        cardCellBinding.cardImage.setImageResource(R.drawable.blyant)
        cardCellBinding.cardTittel.text = post.proveNavn
        cardCellBinding.cardBruker.text = "Bruker ID: ${post.brukerId.toString()}"
        cardCellBinding.btSlett.setOnClickListener{
            clickListener.onClick(post)
        }
    }
}