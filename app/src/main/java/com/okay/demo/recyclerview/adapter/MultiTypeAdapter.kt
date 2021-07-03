package com.okay.demo.recyclerview.adapter

import android.content.Context
import com.okay.demo.R
import com.okay.demo.adapter.CommonRecyclerAdapter
import com.okay.demo.adapter.MultiTypeSupport
import com.okay.demo.adapter.ViewHolder
import com.okay.demo.bean.MultiTypeBean

/**
 *@author wzj
 *@date 3/31/21 4:58 PM
 */
class MultiTypeAdapter(
    context: Context?,
    data: MutableList<MultiTypeBean>?,
    multiTypeSupport: MultiTypeSupport<MultiTypeBean>
) : CommonRecyclerAdapter<MultiTypeBean>(context, data, multiTypeSupport) {
    override fun convert(holder: ViewHolder, item: MultiTypeBean) {
        holder.setText(R.id.content,item.content)
    }
}