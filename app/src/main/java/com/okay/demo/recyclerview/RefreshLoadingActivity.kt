package com.okay.demo.recyclerview

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.okay.demo.R
import com.okay.demo.recyclerview.adapter.HeaderAdapter
import com.okay.demo.recyclerview.adapter.WrapRecyclerAdapter
import com.okay.demo.recyclerview.decoration.CommonItemDecoration
import com.okay.demo.recyclerview.refreshload.DefaultLoadCreator
import com.okay.demo.recyclerview.refreshload.DefaultRefreshCreator
import com.okay.demo.recyclerview.refreshload.RefreshLoadingRecyclerView
import com.okay.demo.recyclerview.refreshload.RefreshRecyclerView
import kotlinx.android.synthetic.main.activity_refresh_loading.*

/**
 *@author wzj
 *@date 4/9/21 2:57 PM
 */
class RefreshLoadingActivity : AppCompatActivity(), RefreshRecyclerView.OnRefreshListener,
    RefreshLoadingRecyclerView.OnLoadListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refresh_loading)
        initData()
    }

    private fun initData() {
        recyclerView.layoutManager = LinearLayoutManager(this)
//        var header = LayoutInflater.from(this).inflate(R.layout.header_test, recyclerView, false)
//        var footer = LayoutInflater.from(this).inflate(R.layout.footer_test, recyclerView,false)

        recyclerView.addItemDecoration(
            CommonItemDecoration(
                ContextCompat.getDrawable(
                    RecyclerViewActivity@ this,
                    R.drawable.divider_line_transparent
                )!!, 2
            )
        )
//        val mutableListOf = mutableListOf<String>()
//        for (index in 0..20) {
//            mutableListOf.add("header and footer $index")
//        }
        recyclerView.addRefreshViewCreator(DefaultRefreshCreator())
        recyclerView.addLoadCreater(DefaultLoadCreator())
        recyclerView.setRefreshListener(this)
        recyclerView.setOnLoadListener(this)
        val homeAdapter = HeaderAdapter()
        val wrapRecyclerAdapter =
            WrapRecyclerAdapter(homeAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>)
        recyclerView.adapter = wrapRecyclerAdapter
    }

    override fun onRefreshing() {
        Handler().postDelayed({
            recyclerView.stopRefreshView()
        }, 2000)
    }

    override fun onLoading() {
        Handler().postDelayed({
            recyclerView.stopLoadView()
        }, 2000)
    }
}