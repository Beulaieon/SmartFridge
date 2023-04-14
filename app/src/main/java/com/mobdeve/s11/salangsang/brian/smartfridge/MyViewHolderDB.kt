package com.mobdeve.s11.salangsang.brian.smartfridge

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.salangsang.brian.smartfridge.databinding.ActivityMyFridgeBinding
import com.mobdeve.s11.salangsang.brian.smartfridge.databinding.FoodLayoutBinding
import com.mobdeve.s11.salangsang.brian.smartfridge.databinding.HomeLayoutBinding
import com.squareup.picasso.Picasso

class MyViewHolderDB(private val viewBinding: FoodLayoutBinding): RecyclerView.ViewHolder(viewBinding.root) {

    fun bindData(data: Food){
        this.viewBinding.foodTitle.text = data.foodname
        this.viewBinding.foodDesc.text = data.description
        this.viewBinding.foodExpiry.text = "Expiry: " + data.expiry

        Log.d("TAG", "bindData: $data")

        Picasso.get()
            .load(Uri.parse(data.imageUri))
            .into(viewBinding.imageIv)
    }

    fun setDeleteBtnOnClickListener(onClickListener: View.OnClickListener?) {
        this.viewBinding.deleteBtn.setOnClickListener(onClickListener)
    }

    fun setEditBtnOnClickListener(onClickListener: View.OnClickListener?) {
        this.viewBinding.editBtn.setOnClickListener(onClickListener)
    }

}

class MyViewHolderHomeDB(private val viewBinding: HomeLayoutBinding): RecyclerView.ViewHolder(viewBinding.root) {

    fun bindData(data: Food){
        this.viewBinding.nameFood.text = data.foodname
        this.viewBinding.descriptionFood.text = data.description

        Log.d("TAG", "bindData: $data")

        Picasso.get()
            .load(Uri.parse(data.imageUri))
            .into(viewBinding.imageIv)
    }

}