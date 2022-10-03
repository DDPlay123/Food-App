package com.side.project.foodapp.utils

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.side.project.foodapp.R

class AnimManager(val context: Context) {

    val smallToLarge: Animation = AnimationUtils.loadAnimation(context, R.anim.small_to_large)
    val largeToSmall: Animation = AnimationUtils.loadAnimation(context, R.anim.large_to_small)

    val rotateOpen: Animation = AnimationUtils.loadAnimation(context, R.anim.rotate_open)
    val rotateClose: Animation = AnimationUtils.loadAnimation(context, R.anim.rotate_closs)

    val fromBottom: Animation = AnimationUtils.loadAnimation(context, R.anim.from_bottom)
    val toBottom: Animation = AnimationUtils.loadAnimation(context, R.anim.to_bottom)

    val fromEnd: Animation = AnimationUtils.loadAnimation(context, R.anim.from_end)
    val toEnd: Animation = AnimationUtils.loadAnimation(context, R.anim.to_end)
}