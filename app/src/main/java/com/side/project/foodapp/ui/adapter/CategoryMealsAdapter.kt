package com.side.project.foodapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.side.project.foodapp.data.model.MealsByCategory
import com.side.project.foodapp.databinding.ItemMealBinding

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.ViewHolder>() {
    private var mealsList = ArrayList<MealsByCategory>()

    @SuppressLint("NotifyDataSetChanged")
    fun setMealList(mealList: ArrayList<MealsByCategory>) {
        this.mealsList = mealList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.imgMeal.load(mealsList[position].strMealThumb)
        holder.binding.tvMeal.text = mealsList[position].strMeal
    }

    override fun getItemCount(): Int = mealsList.size

    class ViewHolder(val binding: ItemMealBinding): RecyclerView.ViewHolder(binding.root)
}