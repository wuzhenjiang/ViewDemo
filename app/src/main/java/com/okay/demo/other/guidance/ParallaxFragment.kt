package com.okay.demo.other.guidance

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.core.view.LayoutInflaterCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.okay.demo.R


/**
 *@author wzj
 *@date 4/10/21 2:44 PM
 */
class ParallaxFragment: Fragment() , LayoutInflater.Factory2 {
    companion object{
        val LAYOUT_ID_KEY = "LAYOUT_ID_KEY"
    }
    private  val TAG = "ParallaxFragment"

    private var mCompatViewInflater: CompatViewInflater? = null
    // 存放所有的需要位移的View
    private val mParallaxViews: MutableList<View> = mutableListOf()

    private val mParallaxAttrs = intArrayOf(
        R.attr.translationXIn,
        R.attr.translationXOut, R.attr.translationYIn, R.attr.translationYOut
    )
//    constructor() : super()
//    constructor(contentLayoutId: Int) : super(contentLayoutId)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var layoutId = arguments!!.getInt(LAYOUT_ID_KEY)
        var tempInflater = inflater
        tempInflater = tempInflater.cloneInContext(activity)
        LayoutInflaterCompat.setFactory2(tempInflater, this)
        return tempInflater.inflate(layoutId,container,false)
    }

    override fun onCreateView(
        parent: View?,
        name: String?,
        context: Context,
        attrs: AttributeSet
    ): View? {
        // View都会来这里,创建View
        // 拦截到View的创建  获取View之后要去解析
        // 1. 创建View
        // If the Factory didn't handle it, let our createView() method try
        val view = createView(parent, name, context, attrs)

        // 2.1 一个activity的布局肯定对应多个这样的 SkinView
        view?.let { analysisAttrs(it, context, attrs) }
        return view
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        Log.d(TAG,"onCreateView  onCreateView")
        return null
    }

    private fun analysisAttrs(
        view: View,
        context: Context,
        attrs: AttributeSet
    ) {
        val array = context.obtainStyledAttributes(attrs, mParallaxAttrs)
        if (array != null && array.indexCount != 0) {
            /* float xIn = array.getFloat(0,0f);
            float xOut = array.getFloat(1,0f);
            float yIn = array.getFloat(2,0f);
            float yOut = array.getFloat(3,0f);*/
            val n = array.indexCount
            val tag = ParallaxTag()
            for (i in 0 until n) {
                val attr = array.getIndex(i)
                when (attr) {
                    0 -> tag.translationXIn = array.getFloat(attr, 0f)
                    1 -> tag.translationXOut = array.getFloat(attr, 0f)
                    2 -> tag.translationYIn = array.getFloat(attr, 0f)
                    3 -> tag.translationYOut = array.getFloat(attr, 0f)
                }
            }
            // 自定义属性怎么存? 还要一一绑定  在View上面设置一个tag
            view.setTag(R.id.parallax_tag, tag)
            //Log.e("TAG",tag.toString());
            mParallaxViews.add(view)
        }
        array.recycle()
    }

    fun createView(
        parent: View?, name: String?, context: Context,
        attrs: AttributeSet
    ): View? {
        val isPre21 = Build.VERSION.SDK_INT < 21
        if (mCompatViewInflater == null) {
            mCompatViewInflater = CompatViewInflater()
        }

        // We only want the View to inherit it's context if we're running pre-v21
        val inheritContext = (isPre21 && true
                && shouldInheritContext(parent as ViewParent?))
        return mCompatViewInflater!!.createView(
            parent, name, context, attrs, inheritContext,
            isPre21,  /* Only read android:theme pre-L (L+ handles this anyway) */
            true /* Read read app:theme as a fallback at all times for legacy reasons */
        )
    }

    private fun shouldInheritContext(parent: ViewParent?): Boolean {
        var parent: ViewParent? = parent
            ?: // The initial parent is null so just return false
            return false
        while (true) {
            if (parent == null) {
                // Bingo. We've hit a view which has a null parent before being terminated from
                // the loop. This is (most probably) because it's the root view in an inflation
                // call, therefore we should inherit. This works as the inflated layout is only
                // added to the hierarchy at the end of the inflate() call.
                return true
            } else if (parent !is View
                || ViewCompat.isAttachedToWindow((parent as View?)!!)
            ) {
                // We have either hit the window's decor view, a parent which isn't a View
                // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
                // is currently added to the view hierarchy. This means that it has not be
                // inflated in the current inflate() call and we should not inherit the context.
                return false
            }
            parent = parent.getParent()
        }
    }

    fun getParallaxViews(): List<View?>? {
        return mParallaxViews
    }
}