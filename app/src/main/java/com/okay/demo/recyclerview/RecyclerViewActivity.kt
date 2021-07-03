package com.okay.demo.recyclerview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.okay.demo.R
import com.okay.demo.main.HomeAdapter
import com.okay.demo.recyclerview.decoration.CommonItemDecoration
import kotlinx.android.synthetic.main.activity_main.*

/**
 *@author wzj
 *@date 3/28/21 4:23 PM
 * RecyclerView优化建议
 * https://juejin.cn/post/6945638073682100260
 */
class RecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)
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
        resources.getStringArray(R.array.RecyclerView).forEach {
            mutableListOf.add(it)
        }
        val homeAdapter = HomeAdapter(this, mutableListOf, R.layout.item_home)
        recyclerView.adapter = homeAdapter
        homeAdapter.setOnItemClickListener {
            when (it) {
                0 -> {
                    val intent = Intent(RecyclerViewActivity@ this, DividerLineActivity::class.java)
                    startActivity(intent)
                }
                1 -> {
                    val intent = Intent(RecyclerViewActivity@ this, StickyActivity::class.java)
                    startActivity(intent)
                }
                2 -> {
                    val intent = Intent(RecyclerViewActivity@ this, MultiTypeActivity::class.java)
                    startActivity(intent)
                }
                3 -> {
                    val intent = Intent(RecyclerViewActivity@ this, HeaderAndFooterActivity::class.java)
                    startActivity(intent)
                }
                4 -> {
                    val intent = Intent(RecyclerViewActivity@ this, RefreshLoadingActivity::class.java)
                    startActivity(intent)
                }
                5 -> {
                    val intent = Intent(RecyclerViewActivity@ this, DragRemoActivity::class.java)
                    startActivity(intent)
                }
                6 -> {
                    val intent = Intent(RecyclerViewActivity@ this, TreeActivity::class.java)
                    startActivity(intent)
                }
                else -> {

                }
            }
        }
    }
}