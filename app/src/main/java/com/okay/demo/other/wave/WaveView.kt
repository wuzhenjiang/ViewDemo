package com.okay.demo.other.wave

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

/**
 *@author wzj
 *@date 4/15/21 4:44 PM
 */
class WaveView : View {
    private val TAG = "WaveView"
    var path = Path()
    private var mHeight = 0
    private var mWidth = 0
    private var mWaveWidth = 0
    private var mWaveHeight = 100

    //取值范围1 ->  0  -> -1  ->0 -> 1循环
    private var mVariable = 1f
    private var mStartX = 0f
    private var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mPaint.color = Color.BLUE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mHeight == 0 || mWidth == 0) {
            mHeight = measuredHeight
            mWidth = measuredWidth
            if (mWidth != 0) {
                mWaveWidth = mWidth / 3
            }
            if (mHeight == 0 || mWidth == 0) {
                return
            }
        }
        path.reset()
        path.moveTo(mStartX, mHeight / 2f)
        path.cubicTo(
            mStartX + mWidth / 4,
            mHeight / 2f + mWaveHeight,
            mStartX + mWidth * 3 / 4,
            mHeight / 2f - mWaveHeight,
            mStartX + mWidth,
            mHeight / 2f
        )
        path.cubicTo(
            mStartX + mWidth * 5 / 4,
            mHeight / 2f + mWaveHeight,
            mStartX + mWidth * 7 / 4,
            mHeight / 2f - mWaveHeight,
            mStartX + 2 * mWidth,
            mHeight / 2f
        )
        path.lineTo(mWidth.toFloat(), mHeight.toFloat())
        path.lineTo(0f, mHeight.toFloat())
        path.close()
        canvas.drawPath(path, mPaint)
    }

    fun startAnimator() {
        post {
            var waveAnimator = ObjectAnimator.ofFloat(-measuredWidth.toFloat(), 0f)
            waveAnimator.addUpdateListener {
                mStartX = it.animatedValue as Float
                invalidate()
            }
            waveAnimator.repeatCount = ObjectAnimator.INFINITE
            waveAnimator.duration = 1000
            waveAnimator.interpolator = LinearInterpolator()
            waveAnimator.start()
        }
    }
}