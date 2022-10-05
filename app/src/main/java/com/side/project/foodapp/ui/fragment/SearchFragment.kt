package com.side.project.foodapp.ui.fragment

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.side.project.foodapp.R
import com.side.project.foodapp.databinding.FragmentSearchBinding
import com.side.project.foodapp.ui.activity.MainActivity
import com.side.project.foodapp.ui.activity.MealActivity
import com.side.project.foodapp.ui.adapter.MealsAdapter
import com.side.project.foodapp.ui.viewModel.MainViewModel
import com.side.project.foodapp.utils.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    private lateinit var searchBinding: FragmentSearchBinding
    private lateinit var mActivity: MainActivity
    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var mealsAdapter: MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as MainActivity
        searchBinding = FragmentSearchBinding.inflate(layoutInflater)
        arguments?.let {}
    }

    override fun onDestroy() {
        mActivity.toggleNavHost(false)
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return searchBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        setListener()
    }

    private fun init() {
        mainViewModel.apply {
            // search meals
            initSearchMealsRV()
            observeSearchedMealsLiceData().observe(viewLifecycleOwner) { mealList ->
                mealList?.let { mealsAdapter.setData(it) }
            }
        }
    }

    private fun initSearchMealsRV() {
        mealsAdapter = MealsAdapter()
        searchBinding.rvSearchMeals.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = mealsAdapter
        }
    }

    private fun setListener() {
        searchBinding.run {
            imgSearch.setOnClickListener { searchMeals() }
            imgBack.setOnClickListener { findNavController().popBackStack() }

            var searchJob: Job? = null
            edSearchBox.addTextChangedListener { searchQuery ->
                imgSearch.imageTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(imgSearch.context,
                        if (searchQuery!!.isEmpty()) R.color.gray else R.color.blue))

                if (searchQuery.isEmpty()) {
                    imgSearch.visibility = View.GONE
                    imgBack.visibility = View.VISIBLE
                } else {
                    imgSearch.visibility = View.VISIBLE
                    imgBack.visibility = View.GONE
                }

                searchJob?.cancel()
                searchJob = Coroutines.main {
                    delay(300)
                    mainViewModel.searchMeals(searchQuery.toString())
                }
            }

            mealsAdapter.onItemClick = { meal ->
                Intent(activity, MealActivity::class.java).apply {
                    this.putExtra(KEY_MEAL_ID, meal.idMeal)
                    this.putExtra(KEY_MEAL_NAME, meal.strMeal)
                    this.putExtra(KEY_MEAL_THUMB, meal.strMealThumb)
                    startActivity(this)
                }
            }

            mealsAdapter.onItemLongClick = { meal ->
                activity?.displayToast(meal.strMeal.toString())
            }
        }
    }

    private fun searchMeals() {
        searchBinding.run {
            val searchQuery = edSearchBox.text.toString()
            if (searchQuery.isNotEmpty())
                mainViewModel.searchMeals(searchQuery)
        }
    }
}