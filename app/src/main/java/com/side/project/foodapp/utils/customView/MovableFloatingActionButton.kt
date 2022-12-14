package com.side.project.foodapp.utils.customView

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.side.project.foodapp.R
import kotlin.math.abs

// https://gist.github.com/arifix/1969e881b0825054bd45fe99d214040f
class MovableFloatingActionButton : FloatingActionButton, View.OnTouchListener {
    private var mVelocityTracker: VelocityTracker? = null
    private var downRawX = 0f
    private var downRawY = 0f
    private var dX = 0f
    private var dY = 0f

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        setOnTouchListener(this)
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        val action = motionEvent.action
        return if (action == MotionEvent.ACTION_DOWN) {
            view.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.accent_dark))

            downRawX = motionEvent.rawX
            downRawY = motionEvent.rawY
            dX = view.x - downRawX
            dY = view.y - downRawY
            true // Consumed
        } else if (action == MotionEvent.ACTION_MOVE) {
            val viewWidth: Int = view.width
            val viewHeight: Int = view.height
            val viewParent: View = view.parent as View
            val parentWidth: Int = viewParent.width
            val parentHeight: Int = viewParent.height

            var newX = motionEvent.rawX + dX
            newX =
                0f.coerceAtLeast(newX) // Don't allow the FAB past the left hand side of the parent
            newX = (parentWidth - viewWidth).toFloat()
                .coerceAtMost(newX) // Don't allow the FAB past the right hand side of the parent

            var newY = motionEvent.rawY + dY
            newY = 0f.coerceAtLeast(newY) // Don't allow the FAB past the top of the parent
            newY = (parentHeight - viewHeight).toFloat()
                .coerceAtMost(newY) // Don't allow the FAB past the bottom of the parent

            view.animate()
                .x(newX)
                .y(newY)
                .setDuration(0)
                .start()
            true // Consumed

        } else if (action == MotionEvent.ACTION_UP) {
            view.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.accent))
            val upRawX = motionEvent.rawX
            val upRawY = motionEvent.rawY
            val upDX = upRawX - downRawX
            val upDY = upRawY - downRawY

            if (abs(upDX) < CLICK_DRAG_TOLERANCE && abs(upDY) < CLICK_DRAG_TOLERANCE) { // A click
                performClick()
            } else { // A drag
                true // Consumed
            }
        } else {
            super.onTouchEvent(motionEvent)
        }
    }

    companion object {
        private const val CLICK_DRAG_TOLERANCE =
            10f // Often, there will be a slight, unintentional, drag when the user taps the FAB, so we need to account for this.
    }
}