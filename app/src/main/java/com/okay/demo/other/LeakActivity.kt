package com.okay.demo.other

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.okay.demo.R
import leakcanary.AppWatcher

/**
 *@author wzj
 *@date 2022/3/13 4:22 下午
 * https://www.jianshu.com/p/eeb97eefea6c
 * https://www.jianshu.com/p/a489e788ab69
 */
class LeakActivity : AppCompatActivity() {
    companion object {
        var haha: LeakActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leak)
        haha = this@LeakActivity
    }

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.expectWeaklyReachable(this, "LeakActivity 没有被回收")
    }
}