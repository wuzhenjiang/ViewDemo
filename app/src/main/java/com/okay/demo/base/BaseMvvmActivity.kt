package com.okay.demo.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

abstract class BaseMvvmActivity<VM : ViewModel?> : AppCompatActivity() {
    var mViewModel: VM? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        //获得泛型参数的实际类型
        val vmClass =
            (this.javaClass.genericSuperclass as ParameterizedType?)!!.actualTypeArguments[0] as Class<VM>
        mViewModel = ViewModelProvider(this).get(vmClass)
    }

    abstract fun getLayoutId(): View
}