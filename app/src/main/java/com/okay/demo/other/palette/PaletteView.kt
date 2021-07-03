package com.okay.demo.other.palette

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 *@author wzj
 *@date 4/23/21 3:37 PM
 */
class PaletteView : View {
    private var mPath = Path()
    private var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mPreX = 0f
    private var mPreY = 0f
    private var mPorterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    private var mBitmap: Bitmap? = null
    private var mCacheCanvas: Canvas? = null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        mPaint.strokeWidth = 10f
        mPaint.style = Paint.Style.STROKE
        mPaint.color = Color.GREEN

        mBitmap = Bitmap.createBitmap(
            resources.displayMetrics.widthPixels,
            resources.displayMetrics.heightPixels,
            Bitmap.Config.ARGB_8888
        )
        mCacheCanvas = Canvas(mBitmap!!)
        mCacheCanvas?.setBitmap(mBitmap)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mCacheCanvas?.drawPath(mPath, mPaint)
        canvas.drawBitmap(mBitmap!!, 0f, 0f, null)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mPreX = event.x
                mPreY = event.y
                mPath.moveTo(mPreX, mPreY)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val lastX = event.x
                val lastY = event.y
                mPath.quadTo(mPreX, mPreY, (mPreX + lastX) / 2, (mPreY + lastY) / 2)
                mPreX = lastX
                mPreY = lastY
            }
            MotionEvent.ACTION_UP -> {
                mPath.reset()
            }
        }
        postInvalidate()
        return super.onTouchEvent(event)
    }

    /**
     * 橡皮擦
     */
    fun setEraser() {
        mPaint.strokeWidth = 50f
        mPaint.xfermode = mPorterDuffXfermode
    }

    /**
     * 设置笔
     */
    fun setBrush() {
        mPaint.strokeWidth = 10f
        mPaint.xfermode = null
    }

    /**
     * 清空
     */
    fun clear() {
        mCacheCanvas?.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        invalidate()
    }
}