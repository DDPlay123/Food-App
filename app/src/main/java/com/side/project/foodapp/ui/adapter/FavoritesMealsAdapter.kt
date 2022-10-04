package com.side.project.foodapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.side.project.foodapp.data.model.Meal
import com.side.project.foodapp.databinding.ItemFavoritesMealBinding
import com.side.project.foodapp.utils.logE

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
    fun setData(mealList: List<Meal>) = differ.submitList(mealList)

    // 讀取 Data
    fun getData(position: Int): Meal = differ.currentList[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemFavoritesMealBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.imgMeal.load(getData(position).strMealThumb) {
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
        holder.binding.tvMealName.text = getData(position).strMeal
        holder.binding.tvCategory.text = getData(position).strCategory
        holder.binding.tvArea.text = getData(position).strArea
    }

    override fun getItemCount(): Int = differ.currentList.size

    class ViewHolder(val binding: ItemFavoritesMealBinding): RecyclerView.ViewHolder(binding.root)
}