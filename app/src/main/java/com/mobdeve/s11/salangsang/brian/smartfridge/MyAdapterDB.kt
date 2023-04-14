package com.mobdeve.s11.salangsang.brian.smartfridge

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.salangsang.brian.smartfridge.databinding.FoodLayoutBinding
import java.util.concurrent.Executors

class MyAdapterDB(private val foods: ArrayList<Food>, private val myActivityResultLauncher: ActivityResultLauncher<Intent>) : RecyclerView.Adapter<MyViewHolderDB>() {

    private lateinit var myDbHelper: MyDBHelper

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderDB {

        val executorService = Executors.newSingleThreadExecutor()

        // This is a way to perform View Binding in the RecyclerView.
        val itemViewBinding: FoodLayoutBinding = FoodLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        val myViewHolder = MyViewHolderDB(itemViewBinding)

        myViewHolder.setEditBtnOnClickListener(View.OnClickListener { view ->
            val i = Intent(view.context, AddFoodData::class.java)
            i.putExtra(IntentKeys.FOOD_NAME_KEY.name, foods[myViewHolder.adapterPosition].foodname)
            i.putExtra(IntentKeys.FOOD_DESCRIPTION_KEY.name, foods[myViewHolder.adapterPosition].description)
            i.putExtra(IntentKeys.FOOD_EXPIRY_KEY.name, foods[myViewHolder.adapterPosition].expiry)
            i.putExtra(IntentKeys.IMAGE_URI_KEY.name, foods[myViewHolder.adapterPosition].imageUri)
            i.putExtra(IntentKeys.FOOD_ID_KEY.name, foods[myViewHolder.adapterPosition].id)
            myActivityResultLauncher.launch(i)
        })

        // Logic for performing the delete operation.
        myViewHolder.setDeleteBtnOnClickListener(View.OnClickListener {

            executorService.execute(Runnable {
                fun run() {
                    myDbHelper = MyDBHelper.getInstance(it.context)!!
                    myDbHelper.deleteFood(foods[myViewHolder.adapterPosition])


                    (it.context as Activity).runOnUiThread { // Remove the appropriate food from the foods ArrayList
                        foods.removeAt(myViewHolder.adapterPosition)
                        // Notify the adapter that the specific item has been removed.
                        notifyItemRemoved(myViewHolder.adapterPosition)
                    }
                }
            })
        })


        return MyViewHolderDB(itemViewBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolderDB, position: Int) {
        // Pass data into the ViewHolder using a single binding method
        holder.bindData(foods[position])

        // Give the delete button logic (Toast + delete data + update view)
        // OnClickListener is defined here so that it has access to the data and passed into the ViewHolder
    }

    override fun getItemCount(): Int {
        return foods.size
    }

}