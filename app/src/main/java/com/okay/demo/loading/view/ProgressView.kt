package com.okay.demo.loading.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.animation.addListener

/**
 *@author wzj
 *@date 2021/7/7 4:10 下午
 */
class ProgressView : View {
    private val TAG = "ProgressView"
    var mGravity = Gravity.top
    var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var mThickness = 20f
    var mFullWidth = 0f
    var mMatrix = Matrix()
    var mOffSet = 0f
    var mLinearGradient: LinearGradient? = null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mPaint.color = Color.WHITE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(
            MeasureSpec.getSize(widthMeasureSpec),
            MeasureSpec.getSize(heightMeasureSpec)
        )
        Log.d(TAG, "measuredWidth   --> $measuredWidth ,measuredHeight--> $measuredHeight")
        mFullWidth = 2f * measuredWidth
        mLinearGradient = LinearGradient(
            -mFullWidth / 2,
            mThickness,
            mFullWidth / 2,
            mThickness,
            intArrayOf(
                Color.parseColor("#00ffffff"),
                Color.parseColor("#ffffffff"),
                Color.parseColor("#00ffffff"),
                Color.parseColor("#ffffffff")
            ),
            floatArrayOf(0f, 0.5f, 0.5f, 1f),
            Shader.TileMode.CLAMP
        )
        mLinearGradient?.setLocalMatrix(mMatrix)
        mPaint.shader = mLinearGradient
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mFullWidth > 0) {
            if (mGravity == Gravity.top) {
                mMatrix.setTranslate(mOffSet, 0f)
                mLinearGradient?.setLocalMatrix(mMatrix)
                canvas?.drawRect(0f, 0f, mFullWidth / 2, mThickness, mPaint)
            }
        }
    }

    fun startAnimator() {
        if (measuredWidth <= 0) {
            post {
                realStartAnimator()
            }
        } else {
            realStartAnimator()
        }
    }

    fun realStartAnimator() {
        Log.d(TAG,"measuredWidth.toFloat() --> $measuredWidth")
        val objectAnimator = ValueAnimator.ofFloat(0f, 1f)
        objectAnimator.addUpdateListener(object:ValueAnimator.AnimatorUpdateListener{
            override fun onAnimationUpdate(p0: ValueAnimator) {
                mOffSet = p0.animatedFraction * measuredWidth
//            Log.d(TAG, "mOffSet --> $mOffSet")
//            Log.d(TAG, "百分比--> ${it.animatedFraction}")
                postInvalidate()
            }

        })
//        objectAnimator.addUpdateListener {
//            mOffSet = it.animatedFraction * measuredWidth
////            Log.d(TAG, "mOffSet --> $mOffSet")
////            Log.d(TAG, "百分比--> ${it.animatedFraction}")
//            postInvalidate()
//        }
        objectAnimator.duration = 5000
        objectAnimator.interpolator = LinearInterpolator()
        objectAnimator.repeatCount = ValueAnimator.INFINITE
        objectAnimator.start()
    }

    enum class Gravity {
        left, top, right, botton
    }
}