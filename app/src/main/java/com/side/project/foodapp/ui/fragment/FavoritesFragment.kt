package com.side.project.foodapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.side.project.foodapp.databinding.FragmentFavoritesBinding
import com.side.project.foodapp.ui.adapter.FavoritesMealsAdapter
import com.side.project.foodapp.ui.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {
    private lateinit var favoritesBinding: FragmentFavoritesBinding
    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var favoritesMealsAdapter: FavoritesMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoritesBinding = FragmentFavoritesBinding.inflate(layoutInflater)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return favoritesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        mainViewModel.apply {
            // get favorites meals
            initFavoritesMealsRV()
            getFavoritesMeals()
            observeFavoritesMealsLiveData().observe(viewLifecycleOwner) { mealList ->
                favoritesMealsAdapter.submitList(mealList)
            }
        }
    }

    private fun initFavoritesMealsRV() {
        favoritesMealsAdapter = FavoritesMealsAdapter()
        favoritesBinding.rvFavorites.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favoritesMealsAdapter
        }
    }
}