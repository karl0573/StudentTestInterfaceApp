package com.example.datahentingtest

import android.icu.text.AlphabeticIndex
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.datahentingtest.model.Post
import com.example.datahentingtest.databinding.ProveCardLayoutBinding

class KortAdapter(private val prove: List<Post>)
    : RecyclerView.Adapter<KortViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KortViewHolder {
        val fra = LayoutInflater.from(parent.context)
        val binding = ProveCardLayoutBinding.inflate(fra, parent, false)
        return KortViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KortViewHolder, position: Int) {
        holder.bindProve(prove[position])
    }

    override fun getItemCount(): Int = prove.size
}