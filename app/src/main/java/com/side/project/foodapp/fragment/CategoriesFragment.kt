package com.side.project.foodapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.side.project.foodapp.databinding.FragmentCategoriesBinding

class CategoriesFragment : Fragment() {
    private lateinit var categoriesBinding: FragmentCategoriesBinding

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
}