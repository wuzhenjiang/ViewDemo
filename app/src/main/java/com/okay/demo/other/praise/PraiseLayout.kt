package com.okay.demo.other.praise

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.widget.AdapterViewAnimator
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.animation.addListener
import androidx.core.content.ContextCompat
import com.okay.demo.R
import java.util.*

/**
 *@author wzj
 *@date 4/5/21 6:53 PM
 */
class PraiseLayout : RelativeLayout {
    private val TAG = "PraiseLayout"
    private var mDrawableIds =
        arrayListOf<Int>(R.drawable.pl_red, R.drawable.pl_blue, R.drawable.pl_yellow)
    private val mRandom = Random()
    private var mDrawableWidth = 0
    private var mDrawableHeight = 0

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        var drawable = ContextCompat.getDrawable(context, R.drawable.pl_red)
        mDrawableWidth = drawable!!.intrinsicWidth
        mDrawableHeight = drawable!!.intrinsicHeight
    }

    fun addView() {
        val imageVew = ImageView(context)
        addView(imageVew)
        val layoutParams = imageVew.layoutParams as RelativeLayout.LayoutParams
        layoutParams.addRule(ALIGN_PARENT_BOTTOM)
        layoutParams.addRule(CENTER_HORIZONTAL)
        imageVew.layoutParams = layoutParams
        var nextInt = mRandom.nextInt(mDrawableIds.size)
        Log.d(TAG, "nextInt = $nextInt")
        imageVew.setImageResource(mDrawableIds[nextInt])
        val allAnimationSet = AnimatorSet()
        val animationSet = getAnimatorSet(imageVew)
        var point0: PointF = PointF(
            (measuredWidth / 2 - mDrawableWidth / 2).toFloat(),
            (measuredHeight - mDrawableHeight).toFloat()
        )

        var point1: PointF = PointF(
            mRandom.nextInt(measuredWidth - mDrawableWidth).toFloat(),
            mRandom.nextInt(measuredHeight / 2).toFloat() + measuredHeight / 2
        )
        var point2: PointF = PointF(
            mRandom.nextInt(measuredWidth - mDrawableWidth).toFloat(),
            mRandom.nextInt(measuredHeight / 2).toFloat()
        )
        val point3: PointF = PointF(
            mRandom.nextInt(measuredWidth - mDrawableWidth).toFloat(),
            0f
        )
        val typeEvaluator = PraiseTypeEvaluator(point1, point2)
        val evaluatorAnimator = ObjectAnimator.ofObject(typeEvaluator, point0, point3)

        evaluatorAnimator.setEvaluator(typeEvaluator)
        evaluatorAnimator.addUpdateListener {
            val pointF = it.animatedValue as PointF
            imageVew.x = pointF.x
            imageVew.y = pointF.y
        }
        evaluatorAnimator.duration = 2000
        allAnimationSet.playSequentially(animationSet, evaluatorAnimator)
        allAnimationSet.start()
        allAnimationSet.addListener(object:AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                removeView(imageVew)
            }
        })
    }

    private fun getAnimatorSet(imageVew: ImageView): AnimatorSet {
        val animationSet = AnimatorSet()
        val alphaAnimator = ObjectAnimator.ofFloat(imageVew, "alpha", 0.3f, 1f)
        val scaleXAnimator = ObjectAnimator.ofFloat(imageVew, "scaleX", 0.3f, 1f)
        val scaleYAnimator = ObjectAnimator.ofFloat(imageVew, "scaleY", 0.3f, 1f)
        animationSet.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator)
        animationSet.duration = 350
        return animationSet
    }
}