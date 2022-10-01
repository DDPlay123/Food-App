package com.side.project.foodapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.side.project.foodapp.data.model.Meal
import com.side.project.foodapp.databinding.ItemMealBinding

/**
 * 來源：https://juejin.cn/post/6903339348754694158
 * 結論：使用 DiffUtil 可以有效提升效能。
 *
 * 原因：使用 notifyDataSetChanged() 有以下缺點。
 * 1. 不會觸發 RecyclerView 動畫。
 * 2. 性能較低，因為是刷新整個 RecyclerView。
 * 3. 圖片會閃爍。
 *
 * 原理：計算新舊資料集的最小更新數，根據情況自動調用以下方法。
 * 1. notifyItemRangeInserted()
 * 2. notifyItemRangeRemoved()
 * 3. notifyItemMoved()
 * 4. notifyItemRangeChanged()
 * **/
class FavoritesMealsAdapter: RecyclerView.Adapter<FavoritesMealsAdapter.ViewHolder>() {

    private val itemCallback = object : DiffUtil.ItemCallback<Meal>() {
        // 比對新舊 Item
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }
        // 比對新舊 Item 內容
        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    // 在背景執行緒比較 Item
    private val differ = AsyncListDiffer(this, itemCallback)

    // 寫入 Data
    fun submitList(mealList: List<Meal>) = differ.submitList(mealList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.imgMeal.load(differ.currentList[position].strMealThumb)
        holder.binding.tvMeal.text = differ.currentList[position].strMeal
    }

    override fun getItemCount(): Int = differ.currentList.size

    class ViewHolder(val binding: ItemMealBinding): RecyclerView.ViewHolder(binding.root)
}