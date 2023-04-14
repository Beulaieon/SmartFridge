package com.mobdeve.s11.salangsang.brian.smartfridge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.salangsang.brian.smartfridge.databinding.HomeLayoutBinding

class MyAdapterHomeDB(private val foods: ArrayList<Food>) : RecyclerView.Adapter<MyViewHolderHomeDB>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderHomeDB {
        // This is a way to perform View Binding in the RecyclerView.
        val itemViewBinding: HomeLayoutBinding = HomeLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolderHomeDB(itemViewBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolderHomeDB, position: Int) {
        // Pass data into the ViewHolder using a single binding method
        holder.bindData(foods[position])

        // Give the delete button logic (Toast + delete data + update view)
        // OnClickListener is defined here so that it has access to the data and passed into the ViewHolder
    }

    override fun getItemCount(): Int {
        return foods.size
    }
}