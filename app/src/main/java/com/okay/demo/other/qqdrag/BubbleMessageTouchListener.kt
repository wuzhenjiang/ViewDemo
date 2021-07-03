package com.okay.demo.other.qqdrag

import android.content.Context
import android.graphics.PixelFormat
import android.graphics.PointF
import android.graphics.drawable.AnimationDrawable
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import com.okay.demo.R


/**
 *@author wzj
 *@date 4/5/21 2:40 PM
 */
open class BubbleMessageTouchListener : View.OnTouchListener, QQDragView.MessageBubbleListener {
    private val TAG = "BubbleMessageTouchListe"

    /**
     * attach进来的View
     */
    private lateinit var mStaticView: View
    private lateinit var mContext: Context
    private lateinit var mDisappearListener: BubbleDisappearListener
    private lateinit var mQQDragView: QQDragView
    private lateinit var mWindowManager: WindowManager
    private lateinit var mLayoutParams: WindowManager.LayoutParams
//    private lateinit var mImageView: ImageView

    // 爆炸动画
    private var mBombFrame: FrameLayout? = null
    private var mBombImage: ImageView? = null

    constructor(staticView: View, context: Context, disappearListener: BubbleDisappearListener) {
        mStaticView = staticView
        mContext = context
        mDisappearListener = disappearListener
        mQQDragView = QQDragView(context)
        mWindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mLayoutParams = WindowManager.LayoutParams()
//        mLayoutParams.format = PixelFormat.TRANSPARENT
        mLayoutParams.flags =
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        mLayoutParams.format = PixelFormat.RGBA_8888
//        mLayoutParams.alpha = 0.0f // 背景透明度
        mBombImage = ImageView(mContext)
        mBombFrame = FrameLayout(mContext)
        mBombImage?.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        mBombFrame?.addView(mBombImage)
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val location = IntArray(2)
                mStaticView.getLocationOnScreen(location)

                val dragBitmap = BubbleUtils.convertViewToBitmap(mStaticView)
                mQQDragView.setDragBitmap(dragBitmap)
                mQQDragView.setMessageBubbleListener(this)
                mWindowManager.addView(mQQDragView, mLayoutParams)
                /**
                 * 初始化的位置在View的中心
                 */
                mQQDragView.initData(
                    location[0] + mStaticView.width / 2f,
                    location[1] + mStaticView.height / 2f - BubbleUtils.getStatusBarHeight(mContext)
                )
                /**
                 * 点击之后隐藏原来的View
                 */
                view.visibility = View.INVISIBLE
            }
            MotionEvent.ACTION_MOVE -> {
                mQQDragView.updateDragPoint(event)
            }
            MotionEvent.ACTION_UP -> {
                mQQDragView.handleActionUp()
            }
        }
        Log.d(TAG, "onTouch")
        return true
    }

    interface BubbleDisappearListener {
        fun dismiss(view: View?)
    }

    override fun restore() {
        // 把原来的View显示
        mStaticView.setVisibility(View.VISIBLE)
        // 把消息的View移除
        mWindowManager.removeView(mQQDragView)
    }

    override fun dismiss(pointF: PointF?) {
        mWindowManager.removeView(mQQDragView)
        mWindowManager.addView(mBombFrame, mLayoutParams)

        mBombImage?.setBackgroundResource(R.drawable.anim_bubble_pop)
        val background = mBombImage?.background as AnimationDrawable
        mBombFrame?.x = pointF!!.x - background.intrinsicWidth / 2
        mBombFrame?.y = pointF!!.y - background.intrinsicHeight / 2
        background.start()
        Handler().postDelayed({
            mWindowManager.removeView(mBombFrame)
            // 通知一下外面该消失
            if (mDisappearListener != null) {
                mDisappearListener.dismiss(mStaticView)
            }
        }, 500)
    }
}