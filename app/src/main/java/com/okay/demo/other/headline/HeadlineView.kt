package com.okay.demo.other.headline

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.okay.demo.R
import kotlin.math.pow
import kotlin.math.sqrt

/**
 *@author wzj
 *@date 4/10/21 3:43 PM
 */
class HeadlineView : LinearLayout {
    private val TAG = "HeadlineView"
    var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var mRadius = 0f

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setWillNotDraw(false)
    }

    private var mBitmap: Bitmap? = null
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
    }

    private fun convertViewToBitmap(view: View): Bitmap? {
        val bitmap =
            Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        //利用bitmap生成画布
        val canvas = Canvas(bitmap)
        //把view中的内容绘制在画布上
        view.draw(canvas)
        return bitmap
    }

    override fun onDraw(canvas: Canvas) {
        if (mBitmap != null) {
            canvas.drawCircle(measuredWidth / 2f, measuredHeight / 2f, mRadius, mPaint)
        }
    }

    fun drawAnimator() {
        val bgView =
            LayoutInflater.from(context).inflate(R.layout.fragment_page_first, this, false)
        bgView.visibility = View.INVISIBLE
        addView(bgView)
        post {
            if (bgView.width > 0 && bgView.height > 0) {
                mBitmap = convertViewToBitmap(bgView)
                removeView(bgView)
                val shader = BitmapShader(mBitmap!!, Shader.TileMode.MIRROR, Shader.TileMode.REPEAT)
                mPaint.shader = shader
                circleAnimator()
            }
        }
    }

    private fun circleAnimator() {
        val radius = sqrt(((measuredWidth / 2f).pow(2) + (measuredHeight / 2f).pow(2)))
        val magnifyAnimator = ObjectAnimator.ofFloat(
            0f,
            radius
        )
        magnifyAnimator.addUpdateListener {
            val animatedValue = it.animatedValue as Float
            mRadius = animatedValue
            invalidate()
        }
        magnifyAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {

            }
        })
        magnifyAnimator.duration = 2000
        magnifyAnimator.start()
    }
}