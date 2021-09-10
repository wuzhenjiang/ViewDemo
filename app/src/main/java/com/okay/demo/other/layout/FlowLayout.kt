package com.okay.demo.other.layout

import android.content.Context
import android.database.DataSetObserver
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup

/**
 *@author wzj
 *@date 2021/9/10 10:10 上午
 * 流式布局
 */
class FlowLayout : ViewGroup {
    private var mAdapter: FlowAdapter? = null
    private var mDataSetObserver = MyDataSetObserver()

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun setAdapter(adapter: FlowAdapter?) {
        if (adapter == null) {
            return
        }
        mAdapter?.unregisterDataSetObserver(mDataSetObserver)
        mAdapter = adapter
        mAdapter?.registerDataSetObserver(mDataSetObserver)
        requestView()
    }

    fun requestView() {
        removeAllViews()
        for (i in 0 until mAdapter!!.counts) {
            addView(mAdapter?.getView(i, null, this))
        }
    }

    inner class MyDataSetObserver : DataSetObserver() {
        override fun onChanged() {
            super.onChanged()
            Log.d("MyDataSetObserver", "MyDataSetObserver")
            requestView()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heighthMode = MeasureSpec.getMode(heightMeasureSpec)
        val heighthSize = MeasureSpec.getSize(heightMeasureSpec)

        val childCount = childCount
        var lineHeight = 0
        var lineWidth = 0
        var height = 0
        var width = 0

        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            measureChild(childView, widthMeasureSpec, heightMeasureSpec)
            val marginLayoutParams = childView.layoutParams as MarginLayoutParams
            val childWidth =
                childView.measuredWidth + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin
            val childHeight =
                childView.measuredHeight + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin
            if (lineWidth + childWidth > widthSize) {
                width = lineWidth.coerceAtLeast(width)
                height += lineHeight

                lineWidth = childWidth
                lineHeight = childHeight
            } else {
                lineWidth += childWidth
                lineHeight = lineHeight.coerceAtLeast(childHeight)
            }

            if (i == childCount - 1) {
                width = lineWidth.coerceAtLeast(width)
                height += lineHeight
            }
        }

        setMeasuredDimension(
            if (widthMode == MeasureSpec.EXACTLY) widthSize else width,
            if (heighthMode == MeasureSpec.EXACTLY) heighthSize else height
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val childCount = childCount
        var lineHeight = 0
        var lineWidth = 0
        var top = 0
        var left = 0
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            val marginLayoutParams = childView.layoutParams as MarginLayoutParams
            val childWidth =
                childView.measuredWidth + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin
            val childHeight =
                childView.measuredHeight + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin

            if (lineWidth + childWidth > measuredWidth) {
                left = 0
                top += lineHeight

                lineHeight = childHeight
                lineWidth = childWidth
            } else {
                lineWidth += childWidth
                lineHeight = lineHeight.coerceAtLeast(childHeight)
            }
            childView.layout(
                left + marginLayoutParams.leftMargin,
                top + marginLayoutParams.topMargin,
                left + childWidth - marginLayoutParams.rightMargin,
                top + childHeight - marginLayoutParams.bottomMargin
            )
            left += childWidth
        }
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(MarginLayoutParams.MATCH_PARENT, MarginLayoutParams.MATCH_PARENT)
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
        return MarginLayoutParams(p)
    }
}