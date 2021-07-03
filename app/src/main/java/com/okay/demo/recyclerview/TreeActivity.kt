package com.okay.demo.recyclerview

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.okay.demo.R
import com.okay.demo.adapter.MultiTypeSupport
import com.okay.demo.bean.TreeBean
import com.okay.demo.recyclerview.adapter.TreeAdapter
import kotlinx.android.synthetic.main.activity_main.*

/**
 *@author wzj
 *@date 4/14/21 10:25 AM
 * 实现原理通过多布局每次点击之后改变数据是否被选中状态从新notifyDataSetChange
 */
class TreeActivity : AppCompatActivity() {
    private val TAG = "TreeActivity111222"
    private val mOriginalDatas = mutableListOf<TreeBean>()
    private val mTempDatas = mutableListOf<TreeBean>()
    private var mTreeAdapter: TreeAdapter? = null
    private lateinit var mToast: Toast
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tree)

        initData()
    }

    private fun initData() {
        mToast = Toast.makeText(TreeActivity@ this, "", Toast.LENGTH_SHORT)
        recyclerView.layoutManager = LinearLayoutManager(this)

        for (i in 0..100) {
            val subsub = arrayListOf<TreeBean>()
            subsub.add(TreeBean(1, 4, R.layout.item_tree_four, "祖孙布局", false, null))
            val sub = arrayListOf<TreeBean>()
            sub.add(TreeBean(2, 3, R.layout.item_tree_third, "孙布局", false, subsub))
            val level2ArrayListOf = arrayListOf<TreeBean>()
            level2ArrayListOf.add(TreeBean(3, 2, R.layout.item_tree_second, "子布局", false, sub))
            val level1TreeBean =
                TreeBean(4, 1, R.layout.item_tree_first, "父布局", false, level2ArrayListOf)

            val level2ArrayListOf12 = arrayListOf<TreeBean>()
            level2ArrayListOf12.add(TreeBean(5, 3, R.layout.item_tree_third, "孙布局", false, null))
            level2ArrayListOf12.add(TreeBean(6, 3, R.layout.item_tree_third, "孙布局", false, null))
            level2ArrayListOf12.add(TreeBean(7, 3, R.layout.item_tree_third, "孙布局", false, null))
            level2ArrayListOf12.add(TreeBean(8, 3, R.layout.item_tree_third, "孙布局", false, null))
            val level2ArrayListOf1 = arrayListOf<TreeBean>()
            level2ArrayListOf1.add(
                TreeBean(9, 2, R.layout.item_tree_second, "子布局", false, null)
            )
            level2ArrayListOf1.add(
                TreeBean(
                    10,
                    2,
                    R.layout.item_tree_second,
                    "子布局",
                    false,
                    level2ArrayListOf12
                )
            )
            level2ArrayListOf1.add(TreeBean(11, 2, R.layout.item_tree_second, "子布局", false, null))
            val level1TreeBean1 =
                TreeBean(12, 1, R.layout.item_tree_first, "父布局", false, level2ArrayListOf1)
            mOriginalDatas.add(level1TreeBean)
            mOriginalDatas.add(level1TreeBean1)
        }

        mTempDatas.addAll(mOriginalDatas)

        mTreeAdapter = TreeAdapter(this, mTempDatas,
            MultiTypeSupport<TreeBean> { item, position -> item.layoutId!! })
        recyclerView.adapter = mTreeAdapter
        mTreeAdapter?.setOnItemClickListener {
            val treeBean = mTempDatas[it]
            treeBean.isOpen = !treeBean.isOpen
            mToast.setText(if (treeBean.isOpen) "打开${treeBean.content}" else "关闭${treeBean.content}")
            mToast.show()
            Log.d(TAG, "修改前：${mTempDatas.toString()}")
            mTempDatas.clear()
            mOriginalDatas.forEachIndexed { index, item ->
                handleData(item)
            }
            Log.d(TAG, "修改后：${mTempDatas.toString()}")
            mTreeAdapter!!.notifyDataSetChanged()
        }
    }

    fun handleData(treeBean: TreeBean) {
        mTempDatas.add(treeBean)
        if (treeBean.isOpen) {
            if (treeBean.subTreeBean != null) {
                treeBean.subTreeBean!!.forEach {
                    handleData(it)
                }
            }
        } else {
            closeTree(treeBean)
        }
    }

    fun closeTree(treeBean: TreeBean) {
        treeBean.isOpen = false
        if (treeBean.subTreeBean != null) {
            treeBean.subTreeBean!!.forEach {
                closeTree(it)
            }
        }
    }

}