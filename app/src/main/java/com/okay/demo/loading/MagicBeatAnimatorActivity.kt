package com.okay.demo.loading

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.okay.demo.R
import com.okay.demo.loading.view.ProgressView

/**
 *@author wzj
 *@date 2021/7/7 7:18 下午
 */
class MagicBeatAnimatorActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.magic_beat_animator)
        var findViewById = findViewById<ProgressView>(R.id.progressView)
        findViewById.post {
            findViewById.startAnimator()
        }
    }
}