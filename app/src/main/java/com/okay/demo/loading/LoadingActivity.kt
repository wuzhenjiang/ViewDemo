package com.okay.demo.loading

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.okay.demo.R
import com.okay.demo.main.HomeAdapter
import com.okay.demo.recyclerview.DividerLineActivity
import com.okay.demo.recyclerview.HeaderAndFooterActivity
import com.okay.demo.recyclerview.MultiTypeActivity
import com.okay.demo.recyclerview.StickyActivity
import com.okay.demo.recyclerview.decoration.CommonItemDecoration
import kotlinx.android.synthetic.main.activity_main.*

/**
 *@author wzj
 *@date 4/3/21 9:37 AM
 */
class LoadingActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
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
        resources.getStringArray(R.array.Loading).forEach {
            mutableListOf.add(it)
        }
        val homeAdapter = HomeAdapter(this, mutableListOf, R.layout.item_home)
        recyclerView.adapter = homeAdapter
        homeAdapter.setOnItemClickListener {
            when (it) {
                0 -> {
                    val intent = Intent(RecyclerViewActivity@ this, BouquetLoadingActivity::class.java)
                    startActivity(intent)
                }
                1 -> {
                    val intent = Intent(RecyclerViewActivity@ this, HeadlineActivity::class.java)
                    startActivity(intent)
                }
                2 -> {
                    val intent = Intent(MainActivity@ this, MagicBeatAnimatorActivity::class.java)
                    startActivity(intent)
                }
                else -> {

                }
            }
        }
    }
}