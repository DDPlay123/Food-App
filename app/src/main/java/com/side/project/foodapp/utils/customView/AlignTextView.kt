package com.side.project.foodapp.utils.customView

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.text.Layout
import android.text.StaticLayout
import android.util.AttributeSet
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatTextView
import com.side.project.foodapp.R

// https://github.com/Amterson/AlginProject
class AlignTextView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatTextView(context, attrs, defStyleAttr) {
    private var alignOnlyOneLine = false

    constructor(context: Context) : this(context, null)
    constructor(context: Context, @Nullable attrs: AttributeSet?) : this(context, attrs, 0)

    private fun init(context: Context, attrs: AttributeSet?) {
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.AlignTextView)
        alignOnlyOneLine = typedArray.getBoolean(R.styleable.AlignTextView_alignOnlyOneLine, false)
        typedArray.recycle()
        setTextColor(currentTextColor)
    }

    override fun setTextColor(color: Int) {
        super.setTextColor(color)
        paint.color = color
    }

    override fun onDraw(canvas: Canvas) {
        val content = text
        if (content !is String) {
            super.onDraw(canvas)
            return
        }
        val layout: Layout = layout
        for (i in 0 until layout.lineCount) {
            val lineBaseline: Int = layout.getLineBaseline(i) + paddingTop
            val lineStart: Int = layout.getLineStart(i)
            val lineEnd: Int = layout.getLineEnd(i)
            if (alignOnlyOneLine && layout.lineCount == 1) { // 只有一行
                val line = content.substring(lineStart, lineEnd)
                val width = StaticLayout.getDesiredWidth(
                    content, lineStart, lineEnd,
                    paint
                )
                drawScaledText(canvas, line, lineBaseline.toFloat(), width)
            } else if (i == layout.lineCount - 1) { // 最后一行
                canvas.drawText(
                    content.substring(lineStart),
                    paddingLeft.toFloat(), lineBaseline.toFloat(), paint
                )
                break
            } else { //中间行
                val line = content.substring(lineStart, lineEnd)
                val width = StaticLayout.getDesiredWidth(
                    content, lineStart, lineEnd,
                    paint
                )
                drawScaledText(canvas, line, lineBaseline.toFloat(), width)
            }
        }
    }

    private fun drawScaledText(canvas: Canvas, line: String, baseLineY: Float, lineWidth: Float) {
        if (line.isEmpty()) {
            return
        }
        var x = paddingLeft.toFloat()
        val forceNextLine = line[line.length - 1].code == 10
        val length = line.length - 1
        if (forceNextLine || length == 0) {
            canvas.drawText(line, x, baseLineY, paint)
            return
        }
        val d = (measuredWidth - lineWidth - paddingLeft - paddingRight) / length
        for (element in line) {
            val c = element.toString()
            val cw = StaticLayout.getDesiredWidth(c, this.paint)
            canvas.drawText(c, x, baseLineY, this.paint)
            x += cw + d
        }
    }

    init {
        init(context, attrs)
    }
}