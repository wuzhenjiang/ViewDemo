package com.okay.demo.recyclerview.adapter

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 *@author wzj
 *@date 4/1/21 4:00 PM
 */
class WrapRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private lateinit var mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>

    /**
     * Header View
     */
    private var mHeaderView = SparseArray<View>()

    /**
     * Footer View
     */
    private var mFooterView = SparseArray<View>()

    private var mHeaderType = 1000000
    private var mFooterType = 2000000

    constructor(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) : super() {
        this.mAdapter = adapter
    }

    fun addHeader(header: View) {
        mHeaderView.append(mHeaderType++, header)
    }

    fun addFooter(footer: View) {
        mFooterView.append(mFooterType++, footer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (mHeaderView[viewType] != null) {
            return object : RecyclerView.ViewHolder(mHeaderView[viewType]) {

            }
        }
        if (mFooterView[viewType] != null) {
            return object : RecyclerView.ViewHolder(mFooterView[viewType]) {

            }
        }
        return mAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return mAdapter.itemCount + mHeaderView.size() + mFooterView.size()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position < mHeaderView.size() || position >= mHeaderView.size() + mAdapter.itemCount) {
            return
        }

        mAdapter.onBindViewHolder(holder, position - mHeaderView.size())
    }

    override fun getItemViewType(position: Int): Int {
        if (position < mHeaderView.size()) {
            return mHeaderView.keyAt(position)
        }
        return if (position < mAdapter.itemCount + mHeaderView.size()) {
            mAdapter.getItemViewType(position - mHeaderView.size())
        } else {
            mFooterView.keyAt(position - mAdapter.itemCount - mHeaderView.size())
        }
    }
}