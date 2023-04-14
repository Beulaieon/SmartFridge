package com.mobdeve.s11.salangsang.brian.smartfridge

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobdeve.s11.salangsang.brian.smartfridge.databinding.ActivityMyFridgeBinding
import java.util.concurrent.Executors

class MyFridge : AppCompatActivity() {

    private val executorService = Executors.newSingleThreadExecutor()
    private lateinit var foods: ArrayList<Food>
    private lateinit var myAdapter: MyAdapterDB
    private lateinit var myDbHelper: MyDBHelper

    private val myActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            if (result.data != null) {
                foods.add(
                    0, Food(
                        result.data!!.getStringExtra(IntentKeys.FOOD_NAME_KEY.name)!!,
                        result.data!!.getStringExtra(IntentKeys.FOOD_DESCRIPTION_KEY.name)!!,
                        result.data!!.getStringExtra(IntentKeys.FOOD_EXPIRY_KEY.name)!!,
                        result.data!!.getStringExtra(IntentKeys.IMAGE_URI_KEY.name)!!
                    )
                )
                myAdapter.notifyItemInserted(0)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding: ActivityMyFridgeBinding = ActivityMyFridgeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)


        //replace this.foods = new ArrayList<>(); with...
        executorService.execute {
            myDbHelper = MyDBHelper.getInstance(this@MyFridge)!!
            foods = myDbHelper.getAllFoodsDefault()

            printFoodsToLog()

            viewBinding.foodRecyclerView.layoutManager = LinearLayoutManager(this@MyFridge)
            myAdapter = MyAdapterDB(foods, myActivityResultLauncher)
            viewBinding.foodRecyclerView.adapter = myAdapter

        }

        viewBinding.addDataBtn.setOnClickListener(View.OnClickListener {
            val i = Intent(this@MyFridge, AddFoodData::class.java)
            myActivityResultLauncher.launch(i)
        })
    }

    private fun printFoodsToLog() {
        for (f in foods) {
            Log.d("MainActivity", "printAllFoods: $f")
        }
    }

}