package com.okay.demo.recyclerview.decoration

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 *@author wzj
 *@date 3/29/21 4:06 PM
 * 注意： 两个item之间的间距是divisionwidth*2
 *
 */
class CommonItemDecoration : RecyclerView.ItemDecoration {
    private val TAG = "GridItemDecoration"
    private val mBounds = Rect()
    private val mTempBounds = Rect()
    private val mDrawable: Drawable?
    private var mDivisionWidth = 0

    constructor(drawable: Drawable, divisionwidth: Int) : super() {
        this.mDrawable = drawable
        this.mDivisionWidth = divisionwidth
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val childCount = parent.childCount
        val layoutManager = parent.layoutManager
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(child, mBounds)
//            if(i>6){
//                return
//            }
//
//            Log.d(TAG,"child left = ${child.left}   top=${child.top}  right=${child.right}  bottom=${child.bottom}")
//            Log.d(TAG,"mBounds left = ${mBounds.left}   top=${mBounds.top}  right=${mBounds.right}  bottom=${mBounds.bottom}")
            if (layoutManager is GridLayoutManager) {
                /**
                 * 左
                 */
                mTempBounds.set(mBounds.left, child.top, child.left, child.bottom)
                mDrawable?.bounds = mTempBounds
                mDrawable?.draw(c)
                /**
                 * 上
                 */
                mTempBounds.set(mBounds.left, mBounds.top, mBounds.right, child.top)
                mDrawable?.bounds = mTempBounds
                mDrawable?.draw(c)
                /**
                 * 右
                 */
                mTempBounds.set(child.right, child.top, mBounds.right, child.bottom)
                mDrawable?.bounds = mTempBounds
                mDrawable?.draw(c)
                /**
                 * 下
                 */
                mTempBounds.set(mBounds.left, child.bottom, mBounds.right, mBounds.bottom)
                mDrawable?.bounds = mTempBounds
                mDrawable?.draw(c)
            } else if (layoutManager is LinearLayoutManager) {
                mTempBounds.set(mBounds.left, child.bottom, mBounds.right, mBounds.bottom)
                mDrawable?.bounds = mTempBounds
                mDrawable?.draw(c)
            }

        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            outRect.set(mDivisionWidth, mDivisionWidth, mDivisionWidth, mDivisionWidth)
        } else if (layoutManager is LinearLayoutManager) {
            outRect.set(0, 0, 0, mDivisionWidth)
        }
    }
}