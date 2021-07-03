package com.okay.demo.other.qqdrag

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.animation.OvershootInterpolator
import kotlin.math.pow
import kotlin.math.sqrt


/**
 *@author wzj
 *@date 4/3/21 4:54 PM
 */
open class QQDragView : View {
    /**
     * 拖拽点
     */
    private var mDragPoint: PointF? = null

    /**
     * 中心点
     */
    private var mCenterPoint: PointF? = null

    private var mDragPointRadius = 10f

    private var mCenterPointMaxRadius = 7f
    private var mCenterPointMinRadius = 3f
    private var mCenterPointRadius = 0f

    private var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mPath = Path()

    private var mShowDrag = true

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mDragPoint = PointF()
        mCenterPoint = PointF()
        mDragPointRadius = dipToPx(mDragPointRadius)
        mCenterPointMaxRadius = dipToPx(mCenterPointMaxRadius)
        mCenterPointMinRadius = dipToPx(mCenterPointMinRadius)
        mPaint.color = Color.RED
        mPaint.isDither = true
    }

    private fun dipToPx(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
    }

    override fun onDraw(canvas: Canvas) {
        if (mDragPoint == null || mCenterPoint == null) {
            return
        }

        /**
         * 画拖动圆
         */
        canvas.drawCircle(mDragPoint!!.x, mDragPoint!!.y, mDragPointRadius, mPaint)

        val distance = getDistance(mDragPoint!!, mCenterPoint!!)
        mCenterPointRadius = (mCenterPointMaxRadius - distance / 14).toFloat()

        /**
         * 画点击位置的圆
         */
        if(mShowDrag){
            if (mCenterPointRadius > mCenterPointMinRadius) {
                canvas.drawCircle(mCenterPoint!!.x, mCenterPoint!!.y, mCenterPointRadius, mPaint)
                drawBessel(canvas, mPath, mCenterPoint!!, mDragPoint!!)
            }else{
                mShowDrag = false
            }
        }

        mDragBitmap?.let {
            canvas.drawBitmap(
                it,
                mDragPoint!!.x - it.width / 2,
                mDragPoint!!.y - it.height / 2,
                null
            )
        }
    }

    /**
     * 画贝塞尔曲线
     */
    private fun drawBessel(canvas: Canvas, path: Path, centerPoint: PointF, dragPoint: PointF) {
        /**
         * 找到贝塞尔曲线的起点终点和控制点的坐标
         */

        val sinA = (dragPoint.y - centerPoint.y) / getDistance(dragPoint, centerPoint)
        val cosA = (dragPoint.x - centerPoint.x) / getDistance(dragPoint, centerPoint)

        val p0x = centerPoint.x + sinA * mCenterPointRadius
        val p0y = centerPoint.y - cosA * mCenterPointRadius

        val p1x = dragPoint.x + sinA * mDragPointRadius
        val p1y = dragPoint.y - cosA * mDragPointRadius

        val p2x = dragPoint.x - sinA * mDragPointRadius
        val p2y = dragPoint.y + cosA * mDragPointRadius

        val p3x = centerPoint.x - sinA * mCenterPointRadius
        val p3y = centerPoint.y + cosA * mCenterPointRadius

        /**
         * 控制点坐标
         */
        val controlX = (centerPoint.x + dragPoint.x) / 2
        val controlY = (centerPoint.y + dragPoint.y) / 2
        path.reset()

        path.moveTo(p0x.toFloat(), p0y.toFloat())
        path.quadTo(controlX, controlY, p1x.toFloat(), p1y.toFloat())
        path.lineTo(p2x.toFloat(), p2y.toFloat())
        path.quadTo(controlX, controlY, p3x.toFloat(), p3y.toFloat())
        path.close()

        canvas.drawPath(path, mPaint)
    }


    /**
     * 计算两点之间距离
     */
    private fun getDistance(dragPoint: PointF, centerPoint: PointF): Double {
        return sqrt(
            (dragPoint.x - centerPoint.x).toDouble()
                .pow(2.0) + (dragPoint.y - centerPoint.y).toDouble().pow(2.0)
        )
    }

//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        when (event.action) {
//            MotionEvent.ACTION_DOWN -> {
//                initData(event)
//            }
//            MotionEvent.ACTION_MOVE -> {
//                updateDragPoint(event)
//            }
//        }
//        invalidate()
//        return true
//    }

    /**
     * 更新拖拽点
     */
    open fun updateDragPoint(event: MotionEvent) {
        mDragPoint!!.set(event.rawX, event.rawY - BubbleUtils.getStatusBarHeight(context))
        // 重新绘制
        invalidate()
    }

    /**
     * 初始化中心点和拖拽点
     */
    open fun initData(x: Float, y: Float) {
        mCenterPoint = PointF(x, y)
        mDragPoint = PointF(x, y)
    }

    companion object {
        fun attach(view: View, listener: BubbleMessageTouchListener.BubbleDisappearListener) {
            view.setOnTouchListener(BubbleMessageTouchListener(view, view.context, listener))
        }
    }

    private var mListener: MessageBubbleListener? = null

    fun setMessageBubbleListener(listener: MessageBubbleListener?) {
        mListener = listener
    }

    fun setDragBitmap(dragBitmap: Bitmap?) {
        mDragBitmap = dragBitmap
    }

    /**
     * 处理抬起事件
     */
    fun handleActionUp() {
        /**
         * 回弹到原来位置
         */
        if (mCenterPointRadius > mCenterPointMinRadius) {
            val objectAnimator = ObjectAnimator.ofFloat(1f, 0f)
            val tempDragPoint = mDragPoint
            objectAnimator.addUpdateListener {
                val animatedValue = it.animatedValue as Float
                mDragPoint =
                    BubbleUtils.getPointByPercent(mCenterPoint, tempDragPoint, animatedValue)
                invalidate()

            }
            objectAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    mListener?.restore()
                    mShowDrag = true
                }
            })
            objectAnimator.interpolator = OvershootInterpolator()

            objectAnimator.duration = if(mShowDrag) 300 else 0
            objectAnimator.start()
        }
        /**
         * 爆炸效果💥
         */
        else {
            // 爆炸
            if (mListener != null) {
                mListener?.dismiss(mDragPoint)
            }
        }
    }

    private var mDragBitmap: Bitmap? = null

    interface MessageBubbleListener {
        // 还原
        fun restore()

        // 消失爆炸
        fun dismiss(pointF: PointF?)
    }

}