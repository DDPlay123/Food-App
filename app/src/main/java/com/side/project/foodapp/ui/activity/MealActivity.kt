package com.side.project.foodapp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.side.project.foodapp.R
import com.side.project.foodapp.data.model.Meal
import com.side.project.foodapp.databinding.ActivityMealBinding
import com.side.project.foodapp.ui.viewModel.MealViewModel
import com.side.project.foodapp.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MealActivity : AppCompatActivity() {
    private lateinit var mealBinding: ActivityMealBinding
    private val mealViewModel: MealViewModel by viewModel()

    private var mealDetail: Meal? = null

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
            mealId = it.getString(KEY_MEAL_ID).toString()
            mealName = it.getString(KEY_MEAL_NAME).toString()
            mealThumb = it.getString(KEY_MEAL_THUMB).toString()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        mealBinding.run {
            imgMealDetail.load(mealThumb)
            collapsingToolbar.title = mealName

            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            toolbar.navigationIcon?.setTint(Color.WHITE)
            toolbar.setNavigationOnClickListener { onBackPressed() }

            mealViewModel.getMealDetail(mealId)
            mealViewModel.observeMealDetailLiveData().observe(this@MealActivity) { meal ->
                toggleLoading(false)

                mealDetail = meal
                mealVideoLink = meal.strYoutube.toString()
                tvCategory.text = "${getString(R.string.text_categories)}：${meal.strCategory}"
                tvArea.text = "${getString(R.string.text_area)}：${meal.strArea}"
                tvRecipe.text = "- ${getString(R.string.text_recipe)}："
                tvRecipeStep.text = meal.strInstructions
            }
        }
    }

    private fun setListener() {
        mealBinding.run {
            appbar.addOnOffsetChangedListener { _, verticalOffset ->
                if (verticalOffset == 0)
                    imgVideo.visibility = View.VISIBLE
                else
                    imgVideo.visibility = View.INVISIBLE
            }

            imgVideo.setOnClickListener {
                if (mealVideoLink.isNotEmpty())
                    Intent(Intent.ACTION_VIEW, Uri.parse(mealVideoLink)).apply {
                        logE("MealLink", mealVideoLink)
                        startActivity(this)
                    }
            }

            fabFavorite.setOnClickListener {
                mealBinding.run {
                    mealDetail?.let { meal ->
                        mealViewModel.insertMeal(meal)
                        displayToast(getString(R.string.hint_already_add_favorite))
                    }
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