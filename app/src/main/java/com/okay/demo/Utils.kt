package com.okay.demo

import android.content.Context
import android.content.Intent

/**
 *@author wzj
 *@date 2022/3/24 2:36 下午
 */

inline fun <reified T> startActivityWithParams(context: Context, block: Intent.() -> Unit) {
    val intent = Intent(context, T::class.java)
    intent.block()
    context.startActivity(intent)
}

inline fun <reified T> startActivityNoParams(context: Context) =
    context.startActivity(Intent(context, T::class.java))