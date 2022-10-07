package com.example.mobprosjekt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobprosjekt.databinding.ProveCardLayoutBinding

class KortAdapter(private val prove: List<Prove>)
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