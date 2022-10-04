package com.side.project.foodapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.side.project.foodapp.data.model.Category
import com.side.project.foodapp.databinding.FragmentCategoriesBinding
import com.side.project.foodapp.ui.activity.CategoryMealsActivity
import com.side.project.foodapp.ui.adapter.CategoriesAdapter
import com.side.project.foodapp.ui.viewModel.MainViewModel
import com.side.project.foodapp.utils.KEY_CATEGORY_NAME
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoriesFragment : Fragment() {
    private lateinit var categoriesBinding: FragmentCategoriesBinding
    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoriesBinding = FragmentCategoriesBinding.inflate(layoutInflater)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return categoriesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        setListener()
    }

    private fun init() {
        mainViewModel.apply {
            // get categories meals
            initCategoriesMealsRV()
            getCategories()
            observeCategoriesLiveData().observe(viewLifecycleOwner) { categories ->
                categoriesAdapter.setCategoryList(categories as ArrayList<Category>)
            }
        }
    }

    private fun setListener() {
        categoriesBinding.run {
            categoriesAdapter.onItemClick = { category ->
                Intent(activity, CategoryMealsActivity::class.java).apply {
                    this.putExtra(KEY_CATEGORY_NAME, category.strCategory)
                    startActivity(this)
                }
            }
        }
    }

    private fun initCategoriesMealsRV() {
        categoriesAdapter = CategoriesAdapter()
        categoriesBinding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }
}