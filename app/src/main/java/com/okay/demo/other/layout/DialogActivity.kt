package com.okay.demo.other.layout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.okay.demo.R
import com.okay.demo.dialog.CommonDialog
import kotlinx.android.synthetic.main.dialog_activity.*

/**
 *@author wzj
 *@date 2021/11/29 5:40 下午
 */
class DialogActivity : AppCompatActivity() {
    var builder: CommonDialog.Builder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_activity)

        showBtn.setOnClickListener {
            builder = CommonDialog.Builder(this)
            builder?.setContentView(R.layout.layout_dialog)?.show()
        }
    }
}