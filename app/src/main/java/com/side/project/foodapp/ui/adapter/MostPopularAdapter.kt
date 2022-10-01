package com.side.project.foodapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.side.project.foodapp.data.model.MealsByCategory
import com.side.project.foodapp.databinding.ItemPopularMealBinding

class MostPopularAdapter : RecyclerView.Adapter<MostPopularAdapter.ViewHolder>() {
    private var mealsList = ArrayList<MealsByCategory>()

    lateinit var onItemClick: ((MealsByCategory) -> Unit)

    @SuppressLint("NotifyDataSetChanged")
    fun setMeals(mealsList: ArrayList<MealsByCategory>) {
        this.mealsList = mealsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemPopularMealBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.imgPopularMealItem.load(mealsList[position].strMealThumb)
        holder.itemView.setOnClickListener { onItemClick.invoke(mealsList[position]) }
    }

    override fun getItemCount(): Int = mealsList.size

    class ViewHolder(val binding: ItemPopularMealBinding): RecyclerView.ViewHolder(binding.root)
}