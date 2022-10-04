package com.side.project.foodapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.side.project.foodapp.data.model.Meal
import com.side.project.foodapp.databinding.DialogBottomPromptBinding
import com.side.project.foodapp.databinding.FragmentFavoritesBinding
import com.side.project.foodapp.ui.DialogManager
import com.side.project.foodapp.ui.activity.MainActivity
import com.side.project.foodapp.ui.adapter.FavoritesMealsAdapter
import com.side.project.foodapp.ui.viewModel.MainViewModel
import com.side.project.foodapp.utils.logE
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {
    private lateinit var favoritesBinding: FragmentFavoritesBinding
    private lateinit var mActivity: MainActivity
    private lateinit var dialog: DialogManager
    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var favoritesMealsAdapter: FavoritesMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as MainActivity
        favoritesBinding = FragmentFavoritesBinding.inflate(layoutInflater)
        dialog = DialogManager.instance(mActivity)
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
            observeFavoritesMealsLiveData().observe(viewLifecycleOwner) { mealList ->
//                mealList.forEach { logE("Meal", it.strMeal.toString()) }
                favoritesMealsAdapter.setData(mealList)
            }
            // set favorites meals touch
            ItemTouchHelper(itemToucheHelper).attachToRecyclerView(favoritesBinding.rvFavorites)
        }
    }

    private fun initFavoritesMealsRV() {
        favoritesMealsAdapter = FavoritesMealsAdapter()
        favoritesBinding.rvFavorites.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = favoritesMealsAdapter
        }
    }

    // 參考：https://blog.csdn.net/a553181867/article/details/54799391
    private val itemToucheHelper = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN, // 上下拖移功能(此處未使用)
        ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT // 左右滑動功能
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val meal: Meal = favoritesMealsAdapter.getData(position)

            val binding = DialogBottomPromptBinding.inflate(layoutInflater)
            dialog.showBottomDialog(binding, false).let {
                binding.run {
                    dialog.setBottomCancelListener(object : DialogManager.BottomCancelListener {
                        override fun response() {
                            favoritesMealsAdapter.notifyItemChanged(position)
                            dialog.cancelBottomDialog()
                        }
                    })
                    cardCancel.setOnClickListener {
                        favoritesMealsAdapter.notifyItemChanged(position)
                        dialog.cancelBottomDialog()
                    }
                    cardDelete.setOnClickListener {
                        mainViewModel.deleteMeal(meal)
                        dialog.cancelBottomDialog()
                    }
                }
            }
        }
    }
}