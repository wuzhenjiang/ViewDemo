package com.okay.demo.other.layout

import android.content.Context
import com.okay.demo.R

/**
 *@author wzj
 *@date 2021/9/10 1:49 下午
 */
public class FlowAdapter : CommonFlowAdapter<String> {
    constructor(context: Context?, datas: MutableList<String>?, layoutId: Int) : super(
        context,
        datas,
        layoutId
    )

    override fun getCount(): Int {
        return mDatas.size
    }

    override fun getItem(position: Int): Any {
        return mDatas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun convert(holder: FlowHolder, item: String, position: Int) {
        holder.setText(R.id.itemTv,item)
    }
}