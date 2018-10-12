package com.cxz.cmbpaydemo;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cmb.pb.util.CMBKeyboardFunc;

public class CmbWebView extends WebView {

    public CmbWebView(Context context) {
        this(context, null);
    }

    public CmbWebView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CmbWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWebView();
    }

    private void initWebView() {
        // 对WebView进行设置
        WebSettings set = getSettings();
        // 支持JS
        set.setJavaScriptEnabled(true);
        set.setSaveFormData(false);
        set.setSavePassword(false);
        set.setSupportZoom(false);
        setWebViewClient(new CmbWebViewClient());

    }

    class CmbWebViewClient extends WebViewClient {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            // 使用当前的WebView加载页面
            CMBKeyboardFunc kbFunc = new CMBKeyboardFunc((Activity) view.getContext());
            if (!kbFunc.HandleUrlCall(view, request.getUrl().toString())) {
                return super.shouldOverrideUrlLoading(view, view.getUrl());
            } else {
                return true;
            }
        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 使用当前的WebView加载页面
            CMBKeyboardFunc kbFunc = new CMBKeyboardFunc((Activity) view.getContext());
            if (!kbFunc.HandleUrlCall(view, url)) {
                return super.shouldOverrideUrlLoading(view, url);
            } else {
                return true;
            }
        }

    }

}
