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
import com.side.project.foodapp.data.model.Category
import com.side.project.foodapp.databinding.ItemCategoryBinding
import com.side.project.foodapp.utils.logE

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
        holder.binding.imgCategory.load(categoriesList[position].strCategoryThumb) {
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
        holder.binding.tvCategoryName.text = categoriesList[position].strCategory
        holder.itemView.setOnClickListener { onItemClick.invoke(categoriesList[position]) }
    }

    override fun getItemCount(): Int = categoriesList.size

    class ViewHolder(val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root)
}