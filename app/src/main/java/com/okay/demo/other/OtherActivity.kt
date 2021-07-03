package com.okay.demo.other

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.okay.demo.R
import com.okay.demo.other.guidance.ParallaxActivity
import com.okay.demo.main.HomeAdapter
import com.okay.demo.other.headline.HeadlineActivity
import com.okay.demo.other.palette.PaletteActivity
import com.okay.demo.other.praise.PraiseActivity
import com.okay.demo.other.qqdrag.QQDragActivity
import com.okay.demo.other.wave.WaveActivity
import com.okay.demo.recyclerview.decoration.CommonItemDecoration
import kotlinx.android.synthetic.main.activity_main.*

/**
 *@author wzj
 *@date 4/3/21 4:46 PM
 */
class OtherActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other)
        initData()
    }

    private fun initData() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            CommonItemDecoration(
                ContextCompat.getDrawable(
                    RecyclerViewActivity@ this,
                    R.drawable.divider_line_transparent
                )!!, 2
            )
        )
        val mutableListOf = mutableListOf<String>()
        resources.getStringArray(R.array.Other).forEach {
            mutableListOf.add(it)
        }
        val homeAdapter = HomeAdapter(this, mutableListOf, R.layout.item_home)
        recyclerView.adapter = homeAdapter
        homeAdapter.setOnItemClickListener {
            when (it) {
                0 -> {
                    val intent = Intent(RecyclerViewActivity@ this, QQDragActivity::class.java)
                    startActivity(intent)
                }
                1 -> {
                    val intent = Intent(RecyclerViewActivity@ this, PraiseActivity::class.java)
                    startActivity(intent)
                }
                2 -> {
                    val intent = Intent(RecyclerViewActivity@ this, ParallaxActivity::class.java)
                    startActivity(intent)
                }
                3 -> {
                    val intent = Intent(RecyclerViewActivity@ this, HeadlineActivity::class.java)
                    startActivity(intent)
                }
                4 -> {
                    val intent = Intent(RecyclerViewActivity@ this, WaveActivity::class.java)
                    startActivity(intent)
                }
                5 -> {
                    val intent = Intent(RecyclerViewActivity@ this, PaletteActivity::class.java)
                    startActivity(intent)
                }
                else -> {

                }
            }
        }
    }
}