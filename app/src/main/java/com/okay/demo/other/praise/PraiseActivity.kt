package com.okay.demo.other.praise

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.okay.demo.R
import kotlinx.android.synthetic.main.activity_praise.*

/**
 *@author wzj
 *@date 4/5/21 6:54 PM
 * 花束点赞效果
 */
class PraiseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_praise)
    }

    fun praise(view: View) {
        praiseLayout.addView()
    }
}