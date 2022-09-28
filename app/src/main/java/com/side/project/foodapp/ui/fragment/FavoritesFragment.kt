package com.side.project.foodapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.side.project.foodapp.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {
    private lateinit var favoritesBinding: FragmentFavoritesBinding

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
}