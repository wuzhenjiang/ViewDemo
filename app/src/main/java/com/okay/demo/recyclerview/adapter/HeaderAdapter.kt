package com.okay.demo.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.okay.demo.R

/**
 *@author wzj
 *@date 4/1/21 5:30 PM
 */
class HeaderAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private var data = arrayListOf<String>()

    constructor() : super(){
        for (index in 0..20) {
            data.add("index $index")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // 先inflate数据
        val rootView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
        return ViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = holder as ViewHolder
        viewHolder.textView.text = data[position]
        viewHolder.textView.setOnClickListener {
            Toast.makeText(viewHolder.textView.context,"点击${data[position]}",Toast.LENGTH_SHORT).show()
        }
    }

    class ViewHolder : RecyclerView.ViewHolder {
        var textView: TextView

        constructor(itemView: View) : super(itemView) {
            textView = itemView.findViewById(R.id.content)
        }
    }
}
