package com.okay.demo.other.headline

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.okay.demo.R
import kotlinx.android.synthetic.main.activity_headline.*

/**
 *@author wzj
 *@date 4/10/21 3:37 PM
 */
class HeadlineActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_headline)
    }

    fun startAnimator(view: View){
        headline.drawAnimator()
    }
}