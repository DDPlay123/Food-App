package com.side.project.foodapp.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.side.project.foodapp.data.MealsByCategory
import com.side.project.foodapp.databinding.ActivityCategoryMealsBinding
import com.side.project.foodapp.ui.adapter.CategoryMealsAdapter
import com.side.project.foodapp.ui.viewModel.CategoryMealsViewModel
import com.side.project.foodapp.utils.KEY_CATEGORY_NAME
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryMealsActivity : AppCompatActivity() {
    private lateinit var categoryMealsBinding: ActivityCategoryMealsBinding
    private val categoryMealsViewModel: CategoryMealsViewModel by viewModel()

    private lateinit var categoryName: String

    private lateinit var categoryMealsAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryMealsBinding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(categoryMealsBinding.root)

        getArguments()
        init()
        setListener()
    }

    private fun getArguments() {
        intent.extras?.let {
            categoryName = it.getString(KEY_CATEGORY_NAME).toString()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        initMealsRV()
        categoryMealsBinding.run {
            categoryMealsViewModel.getMealsByCategory(categoryName)
            categoryMealsViewModel.observeMealsLiveData().observe(this@CategoryMealsActivity) { mealList ->
                tvCategoryCount.text = "${categoryName}：${mealList.size}"
                categoryMealsAdapter.setMealList(mealList as ArrayList<MealsByCategory>)
            }
        }
    }

    private fun setListener() {
        categoryMealsBinding.run {
            imgBack.setOnClickListener { onBackPressed() }
        }
    }

    private fun initMealsRV() {
        categoryMealsAdapter = CategoryMealsAdapter()
        categoryMealsBinding.rvMeals.apply {
            layoutManager = GridLayoutManager(applicationContext, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealsAdapter
        }
    }
}