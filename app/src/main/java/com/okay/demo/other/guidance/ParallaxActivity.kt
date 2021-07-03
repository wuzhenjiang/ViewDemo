package com.okay.demo.other.guidance

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.okay.demo.R
import kotlinx.android.synthetic.main.activity_parallax.*

/**
 *@author wzj
 *@date 4/10/21 11:24 AM
 */
class ParallaxActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parallax)
        parallax_view_pager.setLayouts(supportFragmentManager,
            intArrayOf(R.layout.fragment_page_first,R.layout.fragment_page_second,R.layout.fragment_page_third))
    }
}