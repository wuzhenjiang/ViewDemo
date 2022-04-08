package com.okay.demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.okay.demo.loading.LoadingActivity
import com.okay.demo.main.HomeAdapter
import com.okay.demo.other.LeakActivity
import com.okay.demo.other.OtherActivity
import com.okay.demo.other.picture.PhotoActivity
import com.okay.demo.recyclerview.RecyclerViewActivity
import com.okay.demo.recyclerview.decoration.CommonItemDecoration
import com.okay.demo.web.NotificationActivity
import com.okay.demo.web.WebNativeActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            CommonItemDecoration(
                ContextCompat.getDrawable(
                    RecyclerViewActivity@ this,
                    R.drawable.divider_line_transparent
                )!!, 2
            )
        )
        val mutableListOf = mutableListOf<String>()
        resources.getStringArray(R.array.Names).forEach {
            mutableListOf.add(it)
        }
        val homeAdapter = HomeAdapter(this, mutableListOf, R.layout.item_home)
        recyclerView.adapter = homeAdapter
        homeAdapter.setOnItemClickListener {

            when (it) {
                0 -> startActivityNoParams<RecyclerViewActivity>(this@MainActivity)
                1 -> startActivity<LoadingActivity>()
                2 -> startActivity<OtherActivity>()
                3 -> startActivity<WebNativeActivity>()
                4 -> startActivity<NotificationActivity>()
                5 -> startActivity<LeakActivity>()
                6 -> startActivity<PhotoActivity>()
                else -> {

                }
            }
        }
    }

    private inline fun <reified T>  startActivity(){
        val intent = Intent(MainActivity@ this, T::class.java)
        startActivity(intent)
    }

    private inline fun <reified T>  startActivityWithParams(block:Intent.()->Unit){
        val intent = Intent(MainActivity@ this, T::class.java)
        intent.block()
        startActivity(intent)
    }
}