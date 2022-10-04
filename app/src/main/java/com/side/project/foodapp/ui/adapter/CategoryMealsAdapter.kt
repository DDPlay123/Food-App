package com.side.project.foodapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.side.project.foodapp.data.model.MealsByCategory
import com.side.project.foodapp.databinding.ItemMealBinding
import com.side.project.foodapp.utils.logE

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.ViewHolder>() {
    private var mealsList = ArrayList<MealsByCategory>()

    lateinit var onItemClick: ((MealsByCategory) -> Unit)

    @SuppressLint("NotifyDataSetChanged")
    fun setMealList(mealList: ArrayList<MealsByCategory>) {
        this.mealsList = mealList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.imgMeal.load(mealsList[position].strMealThumb) {
            // 監聽
            listener(
                onStart = {
                    holder.binding.imgPlaceholder.visibility = View.VISIBLE
                },
                onError = { _: ImageRequest, result: ErrorResult ->
                    logE("RandomImage", result.throwable.message.toString())
                    holder.binding.imgPlaceholder.visibility = View.VISIBLE
                },
                onSuccess = { _: ImageRequest, _: SuccessResult ->
                    holder.binding.imgPlaceholder.visibility = View.GONE
                }
            )
        }
        holder.binding.tvMeal.text = mealsList[position].strMeal
        holder.itemView.setOnClickListener { onItemClick.invoke(mealsList[position]) }
    }

    override fun getItemCount(): Int = mealsList.size

    class ViewHolder(val binding: ItemMealBinding): RecyclerView.ViewHolder(binding.root)
}