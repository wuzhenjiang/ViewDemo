package com.okay.demo.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.okay.demo.R
import com.okay.demo.main.HomeAdapter
import com.okay.demo.recyclerview.adapter.HeaderAdapter
import com.okay.demo.recyclerview.adapter.WrapRecyclerAdapter
import com.okay.demo.recyclerview.decoration.CommonItemDecoration
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author wzj
 * @date 4/1/21 11:17 AM
 * @desc 给RecyclerView添加头部和尾部
 * 给adapter包裹一下，在adapter中维护header和footer的列表
 * 然后直接使用包裹的adapter或者自定义一个RecyclerView仿照ListView的方式在内部存放一个能添加header和footer的adapter，感觉第一种方式更好第二种方式耦合太严重，如果后期想加刷新和 加载还要再添加代码
 */
@Suppress("UNCHECKED_CAST")
class HeaderAndFooterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_header_and_footer)
        initData()
    }

    private fun initData() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        var header = LayoutInflater.from(this).inflate(R.layout.header_test, recyclerView, false)
        var footer = LayoutInflater.from(this).inflate(R.layout.footer_test, recyclerView,false)

        recyclerView.addItemDecoration(
            CommonItemDecoration(
                ContextCompat.getDrawable(
                    RecyclerViewActivity@ this,
                    R.drawable.divider_line_transparent
                )!!, 2
            )
        )
        val mutableListOf = mutableListOf<String>()
        for (index in 0..100) {
            mutableListOf.add("header and footer $index")
        }
        val homeAdapter = HeaderAdapter()
        val wrapRecyclerAdapter= WrapRecyclerAdapter(homeAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>)
        wrapRecyclerAdapter.addHeader(header)
        wrapRecyclerAdapter.addFooter(footer)
        recyclerView.adapter = wrapRecyclerAdapter
    }
}