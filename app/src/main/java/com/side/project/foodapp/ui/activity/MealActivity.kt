package com.side.project.foodapp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import coil.load
import com.side.project.foodapp.R
import com.side.project.foodapp.databinding.ActivityMealBinding
import com.side.project.foodapp.ui.viewModel.MealViewModel
import com.side.project.foodapp.utils.KEY_MEAL_ID
import com.side.project.foodapp.utils.KEY_MEAL_NAME
import com.side.project.foodapp.utils.KEY_MEAL_THUMB
import com.side.project.foodapp.utils.logE
import org.koin.androidx.viewmodel.ext.android.viewModel


class MealActivity : AppCompatActivity() {
    private lateinit var mealBinding: ActivityMealBinding
    private val mealViewModel: MealViewModel by viewModel()

    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var mealVideoLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mealBinding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(mealBinding.root)

        toggleLoading(true)
        getArguments()
        init()
        setListener()
    }

    private fun getArguments() {
        intent.extras?.let {
            mealId = it.getString(KEY_MEAL_ID)!!
            mealName = it.getString(KEY_MEAL_NAME)!!
            mealThumb = it.getString(KEY_MEAL_THUMB)!!
        }
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        mealBinding.run {
            imgMealDetail.load(mealThumb)
            collapsingToolbar.title = mealName

            mealViewModel.getMealDetail(mealId)
            mealViewModel.observeMealDetailLiveData().observe(this@MealActivity) { meal ->
                toggleLoading(false)

                mealVideoLink = meal.strYoutube
                tvCategory.text = "${getString(R.string.text_categories)}：${meal.strCategory}"
                tvArea.text = "${getString(R.string.text_area)}：${meal.strArea}"
                tvRecipe.text = "- ${getString(R.string.text_recipe)}："
                tvRecipeStep.text = meal.strInstructions
            }
        }
    }

    private fun setListener() {
        mealBinding.run {
            scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
                if (scrollY == 0)
                    imgVideo.visibility = View.VISIBLE
                else
                    imgVideo.visibility = View.INVISIBLE
                logE("t", scrollY.toString())
            })

            imgVideo.setOnClickListener {
                if (mealVideoLink.isNotEmpty())
                    Intent(Intent.ACTION_VIEW, Uri.parse(mealVideoLink)).apply {
                        logE("MealLink", mealVideoLink)
                        startActivity(this)
                    }
            }
        }
    }

    private fun toggleLoading(isStart: Boolean) {
        mealBinding.run {
            if (isStart) {
                pb.visibility = View.VISIBLE
                fabFavorite.visibility = View.INVISIBLE
                tvCategory.visibility = View.INVISIBLE
                tvArea.visibility = View.INVISIBLE
                tvRecipe.visibility = View.INVISIBLE
                imgVideo.visibility = View.INVISIBLE
            } else {
                pb.visibility = View.INVISIBLE
                fabFavorite.visibility = View.VISIBLE
                tvCategory.visibility = View.VISIBLE
                tvArea.visibility = View.VISIBLE
                tvRecipe.visibility = View.VISIBLE
                imgVideo.visibility = View.VISIBLE
            }
        }
    }
}