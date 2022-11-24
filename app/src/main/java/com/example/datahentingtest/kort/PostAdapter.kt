package com.example.datahentingtest.kort

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.datahentingtest.databinding.ProfilCardLayoutBinding
import com.example.datahentingtest.dataklasser.Kort

class PostAdapter(
    private val prove: List<Kort>,
    private val clickListener: ListeClickListener<Kort>) : RecyclerView.Adapter<PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val fra = LayoutInflater.from(parent.context)
        val binding = ProfilCardLayoutBinding.inflate(fra, parent, false)
        return PostViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bindProve(prove[position])
    }

    override fun getItemCount(): Int = prove.size
}