package com.okay.demo.loading.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

/**
 *@author wzj
 *@date 4/27/21 5:57 PM
 *@des 仿今日头条加载效果
 */
class HeadlineColorView : View {
    var mLinearGradient: LinearGradient? = null
    var mText = "今日头条"
    var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var mRect = Rect()
    var mMatrix = Matrix()
    var mTranslateX = -100f

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        mPaint.color = Color.RED
        mPaint.textSize = 100f
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mPaint.getTextBounds(mText, 0, mText.length, mRect)
        setMeasuredDimension(mRect.width(), mRect.height())

        mLinearGradient = LinearGradient(
            0f,
            0f,
            100f,
            mRect.height().toFloat(),
            intArrayOf(
                Color.parseColor("#22555555"),
                Color.parseColor("#99555555"),
                Color.parseColor("#22555555")
            ),
            floatArrayOf(0f, 0.5f, 1f),
            Shader.TileMode.CLAMP
        )
        mLinearGradient?.setLocalMatrix(mMatrix)
        mPaint.shader = mLinearGradient
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val fontMetricsInt = mPaint.fontMetricsInt
        val dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom
        val baseLine = measuredHeight / 2 + dy

        mMatrix.setTranslate(mTranslateX, 0f)
        mLinearGradient?.setLocalMatrix(mMatrix)
        canvas.drawText(mText, 0f, baseLine.toFloat(), mPaint)
    }

    var animator: ValueAnimator? = null
    fun doAnimator() {
        animator = ObjectAnimator.ofFloat(-100f, measuredWidth + 100f)
        animator?.addUpdateListener {
            mTranslateX = it.animatedValue as Float
            invalidate()
        }
        animator?.duration = 2000
        animator?.repeatCount = ObjectAnimator.INFINITE
        animator?.repeatMode = ObjectAnimator.RESTART
        animator?.interpolator = LinearInterpolator()
        animator?.start()
    }

    fun cancle() {
        animator?.cancel()
    }
}