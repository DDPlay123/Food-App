package com.side.project.foodapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.side.project.foodapp.data.Category
import com.side.project.foodapp.databinding.ItemCategoryBinding

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    private var categoriesList = ArrayList<Category>()

    lateinit var onItemClick: ((Category) -> Unit)

    @SuppressLint("NotifyDataSetChanged")
    fun setCategoryList(categoriesList: ArrayList<Category>) {
        this.categoriesList = categoriesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.imgCategory.load(categoriesList[position].strCategoryThumb)
        holder.binding.tvCategoryName.text = categoriesList[position].strCategory
        holder.itemView.setOnClickListener { onItemClick.invoke(categoriesList[position]) }
    }

    override fun getItemCount(): Int = categoriesList.size

    class ViewHolder(val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root)
}