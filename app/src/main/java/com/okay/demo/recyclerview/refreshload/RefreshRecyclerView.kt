package com.okay.demo.recyclerview.refreshload

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.okay.demo.recyclerview.adapter.WrapRecyclerAdapter

/**
 *@author wzj
 *@date 4/9/21 10:24 AM
 */
open class RefreshRecyclerView : RecyclerView {
    private val TAG = "RefreshRecyclerView"
    var mWrapRecyclerAdapter: WrapRecyclerAdapter? = null
    private var mRefreshCreator: RefreshViewCreator? = null
    private var mHeaderView: View? = null
    private var mHeaderHeight = 0
    var mCoefficient = 0.35f

    private var mDownY = 0f

    // 当前的状态
    private var mCurrentRefreshStatus = 0

    // 默认状态
    private val REFRESH_STATUS_NORMAL = 0x0011

    // 下拉刷新状态
    private val REFRESH_STATUS_PULL_DOWN_REFRESH = 0x0022

    // 松开刷新状态
    private val REFRESH_STATUS_LOOSEN_REFRESHING = 0x0033

    // 正在刷新状态
    private val REFRESH_STATUS_REFRESHING = 0x0044

    //是否在拖拽中
    private var mCurrentDrag = false

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (mHeaderView != null && mHeaderHeight <= 0) {
            mHeaderHeight = mHeaderView!!.measuredHeight
            if (mHeaderHeight > 0) {
                // 隐藏头部刷新的View  marginTop  多留出1px防止无法判断是不是滚动到头部问题
                setRefreshViewMarginTop(-mHeaderHeight + 1)
                Log.d(TAG, "onLayout setRefreshViewMarginTop mHeaderHeight=$mHeaderHeight")
            }
        }
    }

    private fun setRefreshViewMarginTop(marginTop: Int) {
        mHeaderView?.apply {
            var tempMarginTop = marginTop
            if (tempMarginTop < -mHeaderHeight + 1) tempMarginTop = -mHeaderHeight + 1
            val layoutParams = layoutParams as MarginLayoutParams
            layoutParams.topMargin = tempMarginTop
            setLayoutParams(layoutParams)
            Log.d(TAG, "setRefreshViewMarginTop marginTop=$marginTop")
        }
    }


    fun addRefreshViewCreator(refreshCreator: RefreshViewCreator) {
        this.mRefreshCreator = refreshCreator
    }

    private fun addRefreshView() {
        mRefreshCreator?.let {
            val refreshView = it.getRefreshView(context, this)
            mWrapRecyclerAdapter?.addHeader(refreshView)
            mHeaderView = refreshView
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
        if (adapter is WrapRecyclerAdapter) {
            mWrapRecyclerAdapter = adapter
            addRefreshView()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mDownY = ev.rawY
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                if (canScrollDown() || mCurrentRefreshStatus == REFRESH_STATUS_REFRESHING || mRefreshCreator == null) {
                    return super.onTouchEvent(event)
                }

                if (mCurrentDrag) {
                    scrollToPosition(0)
                }

                val diffY = (event.rawY - mDownY) * mCoefficient
                if (diffY > 0) {
                    setRefreshViewMarginTop(diffY.toInt() - mHeaderHeight)
                    updateDragStatus(diffY)
                    mCurrentDrag = true
                    return false
                }
            }
            MotionEvent.ACTION_UP -> {
                if (mCurrentDrag) {
                    restoreRefreshView()
                }
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * 更新当前状态
     */
    private fun updateDragStatus(diffY: Float) {
        mCurrentRefreshStatus = when {
            diffY <= 0 -> {
                REFRESH_STATUS_NORMAL
            }
            diffY < mHeaderHeight -> {
                REFRESH_STATUS_PULL_DOWN_REFRESH
            }
            else -> {
                REFRESH_STATUS_LOOSEN_REFRESHING
            }
        }
        mRefreshCreator?.apply {
            onPull(diffY.toInt(), mHeaderHeight, mCurrentRefreshStatus)
        }
    }

    /**
     * 是否能向下滑动
     */
    private fun canScrollDown(): Boolean {
        return canScrollVertically(-1)
    }

    /**
     * 重置当前刷新状态状态
     */
    private fun restoreRefreshView() {
        val marginLayoutParams = mHeaderView!!.layoutParams as MarginLayoutParams
        val currentTopMargin = marginLayoutParams.topMargin
        var finalTopMargin = -mHeaderHeight + 1
        if (mCurrentRefreshStatus == REFRESH_STATUS_LOOSEN_REFRESHING) {
            finalTopMargin = 0
            mCurrentRefreshStatus = REFRESH_STATUS_REFRESHING
            mRefreshCreator?.apply {
                onRefreshing()
            }

            mRefreshListener?.apply {
                onRefreshing()
            }
        }

        val marginAnimator =
            ObjectAnimator.ofFloat(currentTopMargin.toFloat(), finalTopMargin.toFloat())
        marginAnimator.addUpdateListener {
            val animatedValue = it.animatedValue as Float
            setRefreshViewMarginTop(animatedValue.toInt())
        }
        marginAnimator.duration = (currentTopMargin - finalTopMargin).toLong()
        marginAnimator.start()
        mCurrentDrag = false
    }

    fun stopRefreshView() {
        restoreRefreshView()
        mCurrentRefreshStatus = REFRESH_STATUS_NORMAL
        if (mRefreshCreator != null) {
            mRefreshCreator!!.onStopRefresh()
        }
    }

    private var mRefreshListener: OnRefreshListener? = null

    fun setRefreshListener(listener: OnRefreshListener) {
        this.mRefreshListener = listener
    }

    interface OnRefreshListener {
        fun onRefreshing()
    }
}