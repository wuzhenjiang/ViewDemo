package com.okay.demo.net

import android.os.Bundle
import android.view.View
import com.okay.demo.base.BaseMvvmActivity
import com.okay.demo.databinding.ActivityNetBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *@author wzj
 *@date 2022/3/26 2:43 下午
 */
class NetActivity : BaseMvvmActivity<NetViewModel>() {
    private lateinit var rootView: ActivityNetBinding
    override fun getLayoutId(): View {
        rootView = ActivityNetBinding.inflate(layoutInflater)
        return rootView.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofit = Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
//        retrofit.create()
    }
}