package com.okay.demo.loading.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator

/**
 *@author wzj
 *@date 4/3/21 9:45 AM
 */
class BouquetLoadingView : View {
    private var DEFAULT_RADIO = 20f
    private var DEFAULT_WIDTH = 200f
    private var DEFAULT_HEIGHT = DEFAULT_RADIO
    private var mColors = listOf(Color.RED, Color.GREEN, Color.BLUE)
    private var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mColorChange = 1
    private var mTranslateX = 0f
    private var mStopAnimator = false

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mPaint.style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = MeasureSpec.getMode(heightMeasureSpec)
        if (widthSize < DEFAULT_WIDTH) {
            widthSize = DEFAULT_WIDTH.toInt()
        }

        if (heightSize < 2 * DEFAULT_HEIGHT) {
            heightSize = 2 * DEFAULT_HEIGHT.toInt()
        }
        setMeasuredDimension(widthSize, heightSize)

        mTranslateX = widthSize / 2 - DEFAULT_RADIO
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        /**
         * 画第一个圆圈
         */
        canvas.save()
        canvas.translate(mTranslateX, 0f)
        mPaint.color = mColors[mColorChange % mColors.size]
        canvas.drawCircle(DEFAULT_RADIO, DEFAULT_RADIO, DEFAULT_RADIO, mPaint)
        canvas.restore()




        /**
         * 画第三个圆圈
         */
        canvas.save()
        canvas.translate(-mTranslateX, 0f)
        mPaint.color = mColors[(mColorChange + 2) % mColors.size]
        canvas.drawCircle(measuredWidth - DEFAULT_RADIO, DEFAULT_RADIO, DEFAULT_RADIO, mPaint)
        canvas.restore()

        /**
         * 画第二个圆圈
         */
        canvas.save()
        mPaint.color = mColors[(mColorChange + 1) % mColors.size]
        canvas.drawCircle(measuredWidth / 2f, DEFAULT_RADIO, DEFAULT_RADIO, mPaint)
        canvas.restore()
    }

    private var mValueAnimator: ValueAnimator? = null
    fun starAnimator() {
        mStopAnimator = false
        open()
    }

    private fun close() {
        if(mStopAnimator){
            return
        }
        mValueAnimator = ValueAnimator.ofFloat(
            0f,
            measuredWidth / 2 - DEFAULT_RADIO
        )
        mValueAnimator!!.addUpdateListener {
            mTranslateX = it.animatedValue as Float
            invalidate()
        }
        mValueAnimator!!.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                mColorChange++
                open()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })
        mValueAnimator!!.duration = 500
        mValueAnimator!!.interpolator = AccelerateInterpolator()
        mValueAnimator!!.start()
    }

    fun open(){
        if(mStopAnimator){
            return
        }
        mValueAnimator = ValueAnimator.ofFloat(
            measuredWidth / 2 - DEFAULT_RADIO,
            0f
        )
        mValueAnimator!!.addUpdateListener {
            mTranslateX = it.animatedValue as Float
            invalidate()
        }
        mValueAnimator!!.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                close()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })
        mValueAnimator!!.duration = 500
        mValueAnimator!!.interpolator = DecelerateInterpolator()
        mValueAnimator!!.start()
    }


    fun cancelAnimator() {
        mStopAnimator = true
        mValueAnimator?.let {
            if (it.isRunning) {
                it.cancel()
            }
        }
    }
}