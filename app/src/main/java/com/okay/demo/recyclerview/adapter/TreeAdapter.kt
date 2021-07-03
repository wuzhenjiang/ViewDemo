package com.okay.demo.recyclerview.adapter

import android.content.Context
import com.okay.demo.R
import com.okay.demo.adapter.CommonRecyclerAdapter
import com.okay.demo.adapter.MultiTypeSupport
import com.okay.demo.adapter.ViewHolder
import com.okay.demo.bean.MultiTypeBean
import com.okay.demo.bean.TreeBean

/**
 *@author wzj
 *@date 3/31/21 4:58 PM
 */
class TreeAdapter(
    context: Context?,
    data: MutableList<TreeBean>?,
    multiTypeSupport: MultiTypeSupport<TreeBean>
) : CommonRecyclerAdapter<TreeBean>(context, data, multiTypeSupport) {

    override fun convert(holder: ViewHolder, item: TreeBean) {
        holder.setText(R.id.content,item.content)
    }

}