package com.side.project.foodapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.side.project.foodapp.data.Meal
import com.side.project.foodapp.databinding.FragmentHomeBinding
import com.side.project.foodapp.ui.activity.MealActivity
import com.side.project.foodapp.ui.viewModel.HomeViewModel
import com.side.project.foodapp.utils.KEY_MEAL_ID
import com.side.project.foodapp.utils.KEY_MEAL_NAME
import com.side.project.foodapp.utils.KEY_MEAL_THUMB
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var homeBinding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()

    private lateinit var randomMeal: Meal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = FragmentHomeBinding.inflate(layoutInflater)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        setListener()
    }

    private fun init() {
        homeViewModel.apply {
            // get random meal
            getRandomMeal()
            observeRandomMealLiveData().observe(viewLifecycleOwner) { meal ->
                if (meal.strMealThumb.isEmpty())
                    homeBinding.pbRandomMeal.visibility = View.VISIBLE
                else {
                    randomMeal = meal
                    homeBinding.pbRandomMeal.visibility = View.GONE
                    homeBinding.imgRandomMeal.load(meal.strMealThumb){
                        // 特效，淡入淡出
                        crossfade(true)
                        crossfade(300)
                    }
                }
            }
        }
    }

    private fun setListener() {
        homeBinding.run {
            imgRandomMeal.setOnClickListener {
                Intent(activity, MealActivity::class.java).apply {
                    this.putExtra(KEY_MEAL_ID, randomMeal.idMeal)
                    this.putExtra(KEY_MEAL_NAME, randomMeal.strMeal)
                    this.putExtra(KEY_MEAL_THUMB, randomMeal.strMealThumb)
                    startActivity(this)
                }
            }
        }
    }
}