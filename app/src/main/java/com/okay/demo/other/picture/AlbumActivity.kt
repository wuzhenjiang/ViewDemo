package com.okay.demo.other.picture

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.okay.demo.R
import com.okay.demo.base.BaseMvvmActivity
import com.okay.demo.base.view.GridSpaceDecoration
import com.okay.demo.databinding.ActivityAlbumBinding

/**
 *@author wzj
 *@date 2022/3/25 2:14 下午
 */
class AlbumActivity: BaseMvvmActivity<AlbumViewModel>() {
    private lateinit var rootView : ActivityAlbumBinding
    private lateinit var adapter: AlbumAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        rootView = ActivityAlbumBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val albumRl = rootView.albumRl
        val gridLayoutManager =
            GridLayoutManager(this@AlbumActivity, 3,LinearLayoutManager.VERTICAL, false)
        albumRl.layoutManager = gridLayoutManager
        // 添加分割线
        albumRl.addItemDecoration(
            GridSpaceDecoration(
                resources.getDimension(R.dimen.dp_3).toInt()
            )
        )
        adapter = AlbumAdapter(this,mViewModel?.getUriList(), R.layout.item_album)
        albumRl.adapter = adapter
    }

    override fun getLayoutId(): View {
        rootView = ActivityAlbumBinding.inflate(layoutInflater)
        return rootView.root
    }

}