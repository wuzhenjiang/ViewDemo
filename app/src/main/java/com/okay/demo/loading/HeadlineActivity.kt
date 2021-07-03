package com.okay.demo.loading

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.okay.demo.R
import com.okay.demo.loading.view.HeadlineColorView

/**
 *@author wzj
 *@date 4/27/21 6:19 PM
 */
class HeadlineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_headline_view)
        var headLine = findViewById<HeadlineColorView>(R.id.headLine)
        findViewById<View>(R.id.start).setOnClickListener {
            headLine.doAnimator()
        }
        findViewById<View>(R.id.cancel).setOnClickListener {
            headLine.cancle()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}