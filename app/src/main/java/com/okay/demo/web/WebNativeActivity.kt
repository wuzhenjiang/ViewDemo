package com.okay.demo.web

import android.os.Bundle
import android.util.Log
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.okay.demo.R
import kotlinx.android.synthetic.main.activity_web.*


/**
 *@author wzj
 *@date 2022/1/17 5:24 下午
 * web与原声交互
 * https://www.jianshu.com/p/7551320d19bb
 * https://blog.csdn.net/csdn_aiyang/article/details/72765103
 */
class WebNativeActivity:AppCompatActivity() {
    private val TAG = "WebNativeActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        initWebView()
        webview.loadUrl("file:///android_asset/web/test.html");
        webview.addJavascriptInterface(AndroidForJs(this),"AndroidView");
    }

    private fun initWebView() {
        val webSettings: WebSettings = webview.settings
        webSettings.javaScriptEnabled = true //开启js
        webview.webViewClient = object:WebViewClient(){

            override fun onPageFinished(view: WebView?, url: String?) {

// 1.无参数
//                webview.loadUrl("javascript:showFromAndroid()")
//                Log.d(TAG, "onPageFinished url=$url")

// 2.有参数
//                val msg = "展示内容"
//                webview.loadUrl("javascript:showFromAndroid('$msg')")

// 3.回调
//                val msg = "展示内容"
//                webview.evaluateJavascript("javascript:showFromAndroid('$msg')") {
//                    Log.d(TAG, "onPageFinished evaluateJavascript =$it")
//                }
                super.onPageFinished(view, url)
            }
        }
        webview.webChromeClient = object:WebChromeClient(){
            override fun onJsAlert(
                view: WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                Log.d(TAG, "onJsAlert message=$message,url=$url")
                return super.onJsAlert(view, url, message, result)
            }
        }
    }
}