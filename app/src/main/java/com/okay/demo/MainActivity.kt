package com.okay.demo

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.okay.demo.loading.LoadingActivity
import com.okay.demo.main.HomeAdapter
import com.okay.demo.other.OtherActivity
import com.okay.demo.recyclerview.RecyclerViewActivity
import com.okay.demo.recyclerview.decoration.CommonItemDecoration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        resources.getStringArray(R.array.Names).forEach {
            mutableListOf.add(it)
        }
        val homeAdapter = HomeAdapter(this, mutableListOf, R.layout.item_home)
        recyclerView.adapter = homeAdapter
        homeAdapter.setOnItemClickListener {

            when (it) {
                0 -> {
                    val intent = Intent(MainActivity@ this, RecyclerViewActivity::class.java)
                    startActivity(intent)
                }
                1 -> {
                    val intent = Intent(MainActivity@ this, LoadingActivity::class.java)
                    startActivity(intent)
                }
                2 -> {
                    val intent = Intent(MainActivity@ this, OtherActivity::class.java)
                    startActivity(intent)
                }
                else -> {

                }
            }
        }
    }
}