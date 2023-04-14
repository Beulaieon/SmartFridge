package com.mobdeve.s11.salangsang.brian.smartfridge

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.mobdeve.s11.salangsang.brian.smartfridge.databinding.FragmentHomeBinding
import java.util.concurrent.Executors


class homeFragment : Fragment() {

    private val executorService = Executors.newSingleThreadExecutor()
    private lateinit var foods: ArrayList<Food>
    private lateinit var myAdapter: MyAdapterHomeDB
    private lateinit var myDbHelper: MyDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // perform view binding
        val viewBinding = FragmentHomeBinding.inflate(layoutInflater)


        //replace this.foods = new ArrayList<>(); with...
        executorService.execute {
            myDbHelper = MyDBHelper.getInstance(this@homeFragment)!!
            foods = myDbHelper.getAllFoodsDefault()

            val linearLayoutManager = LinearLayoutManager(activity)
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

            viewBinding.homeRecyclerView.layoutManager = linearLayoutManager
            myAdapter = MyAdapterHomeDB(foods)
            viewBinding.homeRecyclerView.adapter = myAdapter
        }


        // This next code snippet ensures that scroller snaps in place instead of being free position
        // Taken from https://stackoverflow.com/questions/29134094/recyclerview-horizontal-scroll-snap-in-center
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(viewBinding.homeRecyclerView)



        viewBinding.foodBtn.setOnClickListener(View.OnClickListener {
            val intent = Intent (this@homeFragment.context, MyFridge::class.java)
            startActivity(intent)
        })

        return viewBinding.root
    }

    override fun onResume() {
        super.onResume()

        // perform view binding
        val viewBinding = FragmentHomeBinding.inflate(layoutInflater)

        foods = myDbHelper.getAllFoodsDefault()
        myAdapter = MyAdapterHomeDB(foods)
        myAdapter.notifyDataSetChanged()

        viewBinding.homeRecyclerView.adapter = myAdapter

    }

//    override fun onPause() {
//        super.onPause()
//
//        // perform view binding
//        val viewBinding = FragmentHomeBinding.inflate(layoutInflater)
//
//        foods = myDbHelper.getAllFoodsDefault()
//        myAdapter = MyAdapterHomeDB(foods)
//
//        viewBinding.homeRecyclerView.adapter = myAdapter
//        myAdapter.notifyDataSetChanged()
//
//    }


}