package com.example.exampleretrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DogAdapter(private val images:List<String>):RecyclerView.Adapter<DogViewHoldel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHoldel {
        val  layoutInflater = LayoutInflater.from(parent.context)
        return  DogViewHoldel(layoutInflater.inflate(R.layout.item_dog, parent, false))
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: DogViewHoldel, position: Int) {
        val item : String = images[position]
        holder.bind(item)
    }
}