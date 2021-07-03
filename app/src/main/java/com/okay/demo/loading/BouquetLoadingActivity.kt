package com.okay.demo.loading

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.okay.demo.R
import kotlinx.android.synthetic.main.activity_bouquet.*

/**
 * @author wzj
 * @date 4/3/21 9:41 AM
 * @desc 仿花束直播loading效果
 */
class BouquetLoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bouquet)
    }

    fun startAnimator(view: View){
        bouquet.starAnimator()
    }

    fun cancelAnimator(view :View){
        bouquet.cancelAnimator()
    }
}