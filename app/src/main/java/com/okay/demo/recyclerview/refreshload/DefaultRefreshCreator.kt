package com.okay.demo.recyclerview.refreshload

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import com.okay.demo.R

/**
 *@author wzj
 *@date 4/9/21 3:15 PM
 */
class DefaultRefreshCreator : RefreshViewCreator() {
    private val TAG = "DefaultRefreshView"

    // 加载数据的ImageView
    private var mRefreshIv: View? = null

    private var animtorRotation: ObjectAnimator? = null
    override fun getRefreshView(
        context: Context?,
        parent: ViewGroup?
    ): View? {
        val refreshView: View =
            LayoutInflater.from(context).inflate(R.layout.layout_refresh_header_view, parent, false)
        mRefreshIv = refreshView.findViewById(R.id.refresh_iv)
        return refreshView
    }

    override fun onPull(
        currentDragHeight: Int,
        refreshViewHeight: Int,
        currentRefreshStatus: Int
    ) {
        val rotate = currentDragHeight.toFloat() / refreshViewHeight
        // 不断下拉的过程中旋转图片
        mRefreshIv!!.rotation = rotate * 360
//        Log.d(TAG, Log.getStackTraceString(Throwable("onStopRefresh")))
    }

    @SuppressLint("ObjectAnimatorBinding")
    override fun onRefreshing() {
        // 刷新的时候不断旋转
        animtorRotation = ObjectAnimator.ofFloat(
            mRefreshIv,
            "rotation",
            mRefreshIv!!.rotation,
            mRefreshIv!!.rotation + 360
        )
        animtorRotation!!.repeatCount = ObjectAnimator.INFINITE
        animtorRotation!!.duration = 1000
        animtorRotation!!.interpolator = LinearInterpolator()
        animtorRotation!!.start()
    }

    override fun onStopRefresh() {
        Log.d(TAG, Log.getStackTraceString(Throwable("onStopRefresh")))
        // 停止加载的时候清除动画
        mRefreshIv!!.rotation = 0f
        mRefreshIv!!.clearAnimation()
        animtorRotation?.let {
            if (it.isRunning) {
                it.cancel()
            }
        }
        animtorRotation = null
    }
}