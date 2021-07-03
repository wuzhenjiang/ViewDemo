package com.okay.demo.other.qqdrag

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.okay.demo.R
import kotlinx.android.synthetic.main.activity_qq_drag.*

/**
 *@author wzj
 *@date 4/3/21 4:50 PM
 */
class QQDragActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qq_drag)
//        StatusBarUtil.setStatusBarColor(this,R.color.colorPrimaryDark)
        QQDragView.attach(button,object:BubbleMessageTouchListener.BubbleDisappearListener{
            override fun dismiss(view: View?) {
                showToast()
            }
        })

        QQDragView.attach(imageview,object:BubbleMessageTouchListener.BubbleDisappearListener{
            override fun dismiss(view: View?) {
                showToast()
            }
        })
        QQDragView.attach(numRl,object:BubbleMessageTouchListener.BubbleDisappearListener{
            override fun dismiss(view: View?) {
                showToast()
            }
        })
    }

    private fun showToast() {
        Toast.makeText(QQDragActivity@this,"我裂开了",Toast.LENGTH_SHORT).show()
    }
}