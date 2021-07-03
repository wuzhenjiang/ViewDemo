package com.okay.demo.other.palette

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.okay.demo.R
import kotlinx.android.synthetic.main.activity_palette.*

/**
 *@author wzj
 *@date 4/27/21 2:32 PM
 */
class PaletteActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_palette)
        brush.setOnClickListener {
            palette.setBrush()
        }
        eraser.setOnClickListener {
            palette.setEraser()
        }

        clear.setOnClickListener {
            palette.clear()
        }
    }
}