package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import kotlinx.android.synthetic.main.activity_profile.view.*
import ru.skillbranch.devintensive.R

class CircleImageView (
    context: Context,
    attrs: AttributeSet
): ImageView(context, attrs) {
    companion object {
        private const val DEFAULT_BORDER_COLOR = Color.WHITE
        private const val DEFAULT_BORDER_WIDTH = 2f
    }

    private var borderColor = DEFAULT_BORDER_COLOR
    private var borderWidth = DEFAULT_BORDER_WIDTH
    private var strokePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {

            val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
            borderColor = a.getInt(R.styleable.CircleImageView_cv_borderColor, borderColor)
            borderWidth = a.getFloat(R.styleable.CircleImageView_cv_borderWidth, borderWidth)
            strokePaint.color = borderColor
            strokePaint.style = Paint.Style.STROKE
            strokePaint.strokeWidth = borderWidth

            a.recycle()

    }

    fun getBorderWidth() : Float {
        return borderWidth
    }

    fun getBorderColor() : Int {
        return borderColor
    }

    fun setBorderWidth(@Dimension dp: Int) {
        borderWidth = dp.toFloat()
    }

    fun setBorderColor(@ColorRes colorId: Int) {
        borderColor = colorId
    }

    fun setBorderColor(hex: String) {
        borderColor = Color.parseColor(hex)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawOval(iv_avatar.left.toFloat(), iv_avatar.top.toFloat()
            , iv_avatar.left.toFloat() + iv_avatar.width.toFloat()
           , iv_avatar.top.toFloat() + iv_avatar.height.toFloat()  ,  strokePaint)
    }
}