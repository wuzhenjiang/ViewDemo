package com.okay.demo.other.guidance

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.okay.demo.R


/**
 *@author wzj
 *@date 4/10/21 11:29 AM
 */
class ParallaxViewPager : ViewPager {
    var mFragments:ArrayList<ParallaxFragment> = arrayListOf()
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    fun setLayouts(fm: FragmentManager, layoutIds: IntArray) {
        layoutIds.forEach {
            val fragment = ParallaxFragment()
            val bundle = Bundle()
            bundle.putInt(ParallaxFragment.LAYOUT_ID_KEY,it)
            fragment.arguments=bundle
            mFragments.add(fragment)
        }

        adapter = ParallaxPagerAdapter(fm,mFragments)

        addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // 滚动  position 当前位置    positionOffset 0-1     positionOffsetPixels 0-屏幕的宽度px
                Log.e(
                    "TAG",
                    "position->$position positionOffset->$positionOffset positionOffsetPixels->$positionOffsetPixels"
                )

                // 获取左out 右 in
                val outFragment: ParallaxFragment = mFragments[position]
                var parallaxViews: List<View?>? = outFragment.getParallaxViews()
                for (parallaxView in parallaxViews!!) {
                    val tag = parallaxView!!.getTag(R.id.parallax_tag) as ParallaxTag
                    // 为什么这样写 ？
                    parallaxView.translationX = -positionOffsetPixels * tag.translationXOut
                    parallaxView.translationY = -positionOffsetPixels * tag.translationYOut
                }
                try {
                    val inFragment: ParallaxFragment = mFragments[position + 1]
                    parallaxViews = inFragment.getParallaxViews()
                    for (parallaxView in parallaxViews!!) {
                        val tag = parallaxView!!.getTag(R.id.parallax_tag) as ParallaxTag
                        parallaxView.translationX = (measuredWidth - positionOffsetPixels) * tag.translationXIn
                        parallaxView.translationY = (measuredWidth - positionOffsetPixels) * tag.translationYIn
                    }
                } catch (e: Exception) {
                }
            }

            override fun onPageSelected(position: Int) {
                // 选择切换完毕
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    class ParallaxPagerAdapter(
        fm: FragmentManager?,
        var mFragments: ArrayList<ParallaxFragment>
    ) :
        FragmentPagerAdapter(fm!!) {
        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }

        override fun getCount(): Int {
            return mFragments.size
        }
    }
}