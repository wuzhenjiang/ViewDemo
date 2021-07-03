package com.okay.demo.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.okay.demo.R
import com.okay.demo.bean.GroupInfo
import com.okay.demo.recyclerview.adapter.DividerLineItemAdapter
import com.okay.demo.recyclerview.decoration.StickySectionDecoration
import kotlinx.android.synthetic.main.activity_divider_line.*

/**
 *@author wzj
 *@date 3/30/21 7:31 PM
 */
class StickyActivity : AppCompatActivity() {
    private var mutableListOf = mutableListOf<String>()
    private val call =
        StickySectionDecoration.GroupInfoCallback { position ->
            /**
             * 分组逻辑，这里为了测试每5个数据为一组。大家可以在实际开发中
             * 替换为真正的需求逻辑
             */
            /**
             * 分组逻辑，这里为了测试每5个数据为一组。大家可以在实际开发中
             * 替换为真正的需求逻辑
             */
            var groupId = position / 5
            var index = position % 5
            var groupInfo = GroupInfo(groupId, "title $groupId")
            groupInfo.setPosition(index)
            groupInfo.setGroupLength(5)
            groupInfo
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sticky)
        switchLayoutManager()
    }

    private fun switchLayoutManager() {
        recyclerView.layoutManager = LinearLayoutManager(StickyActivity@ this)
        recyclerView.addItemDecoration(StickySectionDecoration(StickyActivity@ this, call))
        for (index in 0 until 56) {
            mutableListOf.add("$index  test ")
        }
        recyclerView.adapter = DividerLineItemAdapter(
            DividerLineActivity@ this,
            mutableListOf,
            R.layout.item_divider_line
        )
    }
}