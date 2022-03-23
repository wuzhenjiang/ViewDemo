package com.okay.demo.web;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class AndroidForJs {
    private Context context;

    public AndroidForJs(Context context) {
        this.context = context;
    }

    //api17以后，只有public且添加了 @JavascriptInterface 注解的方法才能被调用
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(context, "js调用android showToast():" + toast, Toast.LENGTH_SHORT).show();
    }

}