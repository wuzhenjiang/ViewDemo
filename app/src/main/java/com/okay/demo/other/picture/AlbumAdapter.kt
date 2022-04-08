package com.okay.demo.other.picture

import android.content.Context
import android.net.Uri
import com.bumptech.glide.Glide
import com.okay.demo.R
import com.okay.demo.adapter.CommonRecyclerAdapter
import com.okay.demo.adapter.ViewHolder

/**
 *@author wzj
 *@date 2022/3/25 5:03 下午
 */
class AlbumAdapter(context: Context, data: MutableList<Uri>?, layoutId: Int):CommonRecyclerAdapter<Uri>(context, data, layoutId) {
    override fun convert(holder: ViewHolder, item: Uri) {
        Glide.with(mContext).asBitmap().load(item).into(holder.getView(R.id.albumIv))
    }
}