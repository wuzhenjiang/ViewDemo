package com.okay.demo.recyclerview.adapter

import android.content.Context
import com.okay.demo.R
import com.okay.demo.adapter.CommonRecyclerAdapter
import com.okay.demo.adapter.ViewHolder

/**
 *@author wzj
 *@date 3/28/21 5:30 PM
 */
class DividerLineItemAdapter(context: Context, data: MutableList<String>, layoutId: Int) :
    CommonRecyclerAdapter<String>(context, data, layoutId) {
    override fun convert(holder: ViewHolder, item: String) {
        holder.setText(R.id.content, item)
    }
}