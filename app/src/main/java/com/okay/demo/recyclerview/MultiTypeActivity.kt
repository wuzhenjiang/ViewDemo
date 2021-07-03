package com.okay.demo.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.okay.demo.R
import com.okay.demo.adapter.MultiTypeSupport
import com.okay.demo.bean.MultiTypeBean
import com.okay.demo.recyclerview.adapter.MultiTypeAdapter
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author wzj
 * @date 3/31/21 4:54 PM
 */
class MultiTypeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_type)

        initData()
    }

    private fun initData() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        val mutableListOf = mutableListOf<MultiTypeBean>()
        for (index in 0..100) {
            var multiTypeBean = MultiTypeBean(0, "")
            when (index % 3) {
                0 -> {
                    multiTypeBean.layoutId = R.layout.item_multi_type_left
                    multiTypeBean.content = "布局1 index =$index"
                }
                1 -> {
                    multiTypeBean.layoutId = R.layout.item_multi_type_middle
                    multiTypeBean.content = "布局2 index =$index"
                }
                2 -> {
                    multiTypeBean.layoutId = R.layout.item_multi_type_right
                    multiTypeBean.content = "布局3 index =$index"
                }
            }
            mutableListOf.add(multiTypeBean)
        }
        val homeAdapter = MultiTypeAdapter(this, mutableListOf,
            MultiTypeSupport<MultiTypeBean> { item, position -> item.layoutId!! })
        recyclerView.adapter = homeAdapter
    }
}