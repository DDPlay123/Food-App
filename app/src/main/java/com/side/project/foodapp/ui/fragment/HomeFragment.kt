package com.side.project.foodapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.side.project.foodapp.R
import com.side.project.foodapp.data.model.Category
import com.side.project.foodapp.data.model.MealsByCategory
import com.side.project.foodapp.data.model.Meal
import com.side.project.foodapp.databinding.DialogBottomSheetMealBinding
import com.side.project.foodapp.databinding.FragmentHomeBinding
import com.side.project.foodapp.ui.DialogManager
import com.side.project.foodapp.ui.activity.CategoryMealsActivity
import com.side.project.foodapp.ui.activity.MainActivity
import com.side.project.foodapp.ui.activity.MealActivity
import com.side.project.foodapp.ui.adapter.CategoriesAdapter
import com.side.project.foodapp.ui.adapter.MostPopularAdapter
import com.side.project.foodapp.ui.viewModel.MainViewModel
import com.side.project.foodapp.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var mActivity: MainActivity
    private lateinit var dialog: DialogManager
    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var randomMeal: Meal

    private lateinit var mostPopularAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as MainActivity
        homeBinding = FragmentHomeBinding.inflate(layoutInflater)
        dialog = DialogManager.instance(mActivity)
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
        mainViewModel.apply {
            // get random meal
            getRandomMeal()
            observeRandomMealLiveData().observe(viewLifecycleOwner) { meal ->
                if (meal.strMealThumb!!.isEmpty())
                    homeBinding.imgPlaceholder.visibility = View.VISIBLE
                else {
                    randomMeal = meal
                    homeBinding.imgRandomMeal.load(meal.strMealThumb){
                        // 特效，淡入淡出
                        crossfade(true)
                        crossfade(300)
                        // 監聽
                        listener(
                            onStart = {
                                homeBinding.imgPlaceholder.visibility = View.VISIBLE
                            },
                            onError = { _: ImageRequest, result: ErrorResult ->
                                logE("RandomImage", result.throwable.message.toString())
                                homeBinding.imgPlaceholder.visibility = View.VISIBLE
                            },
                            onSuccess = { _: ImageRequest, _: SuccessResult ->
                                homeBinding.imgPlaceholder.visibility = View.GONE
                            }
                        )
                    }
                }
            }

            // get popular meal
            initPopularMealRV()
            getPopularItems()
            observePopularItemLiveData().observe(viewLifecycleOwner) { mealList ->
                mostPopularAdapter.setMeals(mealList as ArrayList<MealsByCategory>)
            }

            // get categories
            initCategoriesRV()
            getCategories()
            observeCategoriesLiveData().observe(viewLifecycleOwner) { categories ->
                categoriesAdapter.setCategoryList(categories as ArrayList<Category>)
            }
        }
    }

    private fun setListener() {
        homeBinding.run {
            imgRandomMeal.setOnClickListener {
                val binding = DialogBottomSheetMealBinding.inflate(layoutInflater)
                dialog.showBottomDialog(binding, false).let {
                    binding.run {
                        layoutContent.visibility = View.VISIBLE
                        tvHeader.text = getString(R.string.hint_random_meal)
                        tvMealName.text = randomMeal.strMeal
                        tvCategory.text = randomMeal.strCategory
                        tvArea.text = randomMeal.strArea
                        imgMeal.load(randomMeal.strMealThumb) {
                            // 監聽
                            listener(
                                onStart = {
                                    imgPlaceholder.visibility = View.VISIBLE
                                },
                                onError = { _: ImageRequest, result: ErrorResult ->
                                    logE("RandomImage", result.throwable.message.toString())
                                    imgPlaceholder.visibility = View.VISIBLE
                                },
                                onSuccess = { _: ImageRequest, _: SuccessResult ->
                                    imgPlaceholder.visibility = View.GONE
                                }
                            )
                        }
                        btnGoDetail.setOnClickListener {
                            Intent(activity, MealActivity::class.java).apply {
                                this.putExtra(KEY_MEAL_ID, randomMeal.idMeal)
                                this.putExtra(KEY_MEAL_NAME, randomMeal.strMeal)
                                this.putExtra(KEY_MEAL_THUMB, randomMeal.strMealThumb)
                                startActivity(this)
                                dialog.cancelBottomDialog()
                            }
                        }
                    }
                }
            }

            mostPopularAdapter.onItemClick = { meal ->
                val binding = DialogBottomSheetMealBinding.inflate(layoutInflater)
                dialog.showBottomDialog(binding, false).let {
                    binding.run {
                        layoutContent.visibility = View.GONE
                        tvHeader.text = getString(R.string.over_popular_items)
                        tvMealName.text = meal.strMeal
                        imgMeal.load(meal.strMealThumb) {
                            // 監聽
                            listener(
                                onStart = {
                                    imgPlaceholder.visibility = View.VISIBLE
                                },
                                onError = { _: ImageRequest, result: ErrorResult ->
                                    logE("RandomImage", result.throwable.message.toString())
                                    imgPlaceholder.visibility = View.VISIBLE
                                },
                                onSuccess = { _: ImageRequest, _: SuccessResult ->
                                    imgPlaceholder.visibility = View.GONE
                                }
                            )
                        }
                        btnGoDetail.setOnClickListener {
                            Intent(activity, MealActivity::class.java).apply {
                                this.putExtra(KEY_MEAL_ID, meal.idMeal)
                                this.putExtra(KEY_MEAL_NAME, meal.strMeal)
                                this.putExtra(KEY_MEAL_THUMB, meal.strMealThumb)
                                startActivity(this)
                                dialog.cancelBottomDialog()
                            }
                        }
                    }
                }
            }

            categoriesAdapter.onItemClick = { category ->
                Intent(activity, CategoryMealsActivity::class.java).apply {
                    this.putExtra(KEY_CATEGORY_NAME, category.strCategory)
                    startActivity(this)
                }
            }

            imgSearch.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
                mActivity.toggleNavHost(true)
            }
        }
    }

    private fun initPopularMealRV() {
        mostPopularAdapter = MostPopularAdapter()
        homeBinding.rvPopularMeal.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = mostPopularAdapter
            offsetLeftAndRight(5)
        }
    }

    private fun initCategoriesRV() {
        categoriesAdapter = CategoriesAdapter()
        homeBinding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }
}