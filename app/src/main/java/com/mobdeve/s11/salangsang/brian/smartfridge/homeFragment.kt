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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [homeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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

    
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment homeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            homeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}