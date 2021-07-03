package com.okay.demo.other.wave

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.okay.demo.R

/**
 *@author wzj
 *@date 4/16/21 11:12 AM
 */
class WaveActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wave)
        var waveView = findViewById<WaveView>(R.id.wave)
        waveView.startAnimator()
    }
}