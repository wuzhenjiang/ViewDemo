package com.okay.demo.other.layout

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.okay.demo.R
import kotlin.random.Random

/**
 *@author wzj
 *@date 2021/9/10 11:09 上午
 */
class FlowActivity : AppCompatActivity() {
    lateinit var mFlowLayout: FlowLayout
    lateinit var mCommonFlowAdapter: FlowAdapter
    val data = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow)
        mFlowLayout = findViewById<FlowLayout>(R.id.flowLayout)

        for (i in 0..20) {
            data.add("${Random.nextInt(100000)}")
        }
        mCommonFlowAdapter = FlowAdapter(this, data, R.layout.item_flow)
        mFlowLayout.setAdapter(mCommonFlowAdapter)
    }

    fun addView(view: View) {
        data.add("${Random.nextInt(100000)}")
        mCommonFlowAdapter.notifyDataSetChanged()
    }

    fun removeView(view: View) {
        if (data.size <= 0) {
            return
        }
        data.removeAt(0)
        mCommonFlowAdapter.notifyDataSetChanged()
    }
}