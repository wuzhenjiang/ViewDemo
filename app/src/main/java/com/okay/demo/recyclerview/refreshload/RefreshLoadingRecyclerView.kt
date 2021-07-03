package com.okay.demo.recyclerview.refreshload

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import com.okay.demo.recyclerview.adapter.WrapRecyclerAdapter

/**
 *@author wzj
 *@date 4/12/21 2:55 PM
 */
class RefreshLoadingRecyclerView :
    RefreshRecyclerView {
    private val TAG = "RefreshLoadingView"
    private var mLoadViewCreator: LoadViewCreator? = null
    private var mLoadView: View? = null
    private var mLoadViewHeight = 0

    // 手指按下的Y位置
    private var mFingerDownY = 0f

    // 当前是否正在拖动
    private var mCurrentLoadViewDrag = false

    // 当前的状态
    private var mCurrentLoadStatus = 0

    // 默认状态
    private var LOAD_STATUS_NORMAL = 0x0011

    // 上拉加载更多状态
    private var LOAD_STATUS_PULL_DOWN_REFRESH = 0x0022

    // 松开加载更多状态
    private var LOAD_STATUS_LOOSEN_LOADING = 0x0033

    // 正在加载更多状态
    private var LOAD_STATUS_LOADING = 0x0044

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    private fun dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            resources.displayMetrics
        ).toInt()
    }

    /**
     * 添加加载更多View
     */
    fun addLoadCreater(loadViewCreator: LoadViewCreator) {
        this.mLoadViewCreator = loadViewCreator
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
        if (adapter is WrapRecyclerAdapter) {
            mWrapRecyclerAdapter = adapter
            addLoadView()
        }
    }

    private fun addLoadView() {
        mLoadViewCreator?.let {
            val loadView = it.getLoadView(
                context,
                this
            )
            mWrapRecyclerAdapter?.addFooter(loadView)
            mLoadView = loadView
            mLoadViewHeight = dpToPx(80)
            setLoadViewMarginBottom(-mLoadViewHeight)
            Log.d(TAG, "addLoadView addFooter")
        }
    }

    private fun setLoadViewMarginBottom(marginBottom: Int) {
        mLoadView?.apply {
            var tempMarginBottom = marginBottom
            if (tempMarginBottom < -mLoadViewHeight) {
                tempMarginBottom = -mLoadViewHeight
            }
            val marginLayoutParams = layoutParams as MarginLayoutParams
            marginLayoutParams.bottomMargin = tempMarginBottom
            layoutParams = marginLayoutParams
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mFingerDownY = ev.rawY
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                if (canScrollUp() || mCurrentLoadStatus == LOAD_STATUS_LOADING) {
                    return super.onTouchEvent(event)
                }

                if (mCurrentLoadViewDrag) {
                    scrollToPosition(adapter!!.itemCount - 1)
                }

                val diffY = (event.rawY - mFingerDownY) * mCoefficient
                if (diffY < 0) {
                    setLoadViewMarginBottom(-diffY.toInt() - mLoadViewHeight)
                    updateDragStatus(-diffY)
                    mCurrentLoadViewDrag = true
                    return true
                }
            }
            MotionEvent.ACTION_UP -> {
                if (mCurrentLoadViewDrag) {
                    restoreLoadView()
                }
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * 重置当前刷新状态状态
     */
    private fun restoreLoadView() {
        val marginLayoutParams = mLoadView!!.layoutParams as MarginLayoutParams
        val currentBottomMargin = marginLayoutParams.bottomMargin
        var finalBottomMargin = -mLoadViewHeight
        if (mCurrentLoadStatus == LOAD_STATUS_LOOSEN_LOADING) {
            finalBottomMargin = 0
            mCurrentLoadStatus = LOAD_STATUS_LOADING
            mLoadViewCreator?.apply {
                onLoading()
            }
            mOnLoadListener?.apply {
                onLoading()
            }
        }
        Log.d(TAG, "currentTopMargin=$currentBottomMargin ,finalTopMargin=$finalBottomMargin")
        val marginAnimator =
            ObjectAnimator.ofFloat(currentBottomMargin.toFloat(), finalBottomMargin.toFloat())
        marginAnimator.addUpdateListener {
            val animatedValue = it.animatedValue as Float
            setLoadViewMarginBottom(animatedValue.toInt())
        }
        marginAnimator.duration = (currentBottomMargin - finalBottomMargin).toLong()
        marginAnimator.start()
        mCurrentLoadViewDrag = false
    }

    private fun updateDragStatus(diffY: Float) {
        mCurrentLoadStatus = when {
            diffY <= 0 -> {
                LOAD_STATUS_NORMAL
            }
            diffY < mLoadViewHeight -> {
                LOAD_STATUS_PULL_DOWN_REFRESH
            }
            else -> {
                LOAD_STATUS_LOOSEN_LOADING
            }
        }
        mLoadViewCreator?.apply {
            onPull(diffY.toInt(), mLoadViewHeight, mCurrentLoadStatus)
        }
    }

    /**
     * 是否能向下滑动
     */
    private fun canScrollUp(): Boolean {
        return canScrollVertically(1)
    }

    fun stopLoadView() {
        restoreLoadView()
        mCurrentLoadStatus = LOAD_STATUS_NORMAL
        mLoadViewCreator?.apply {
            onStopLoad()
        }
    }

    private var mOnLoadListener: OnLoadListener? = null

    fun setOnLoadListener(listener: OnLoadListener) {
        this.mOnLoadListener = listener
    }

    interface OnLoadListener {
        fun onLoading()
    }
}