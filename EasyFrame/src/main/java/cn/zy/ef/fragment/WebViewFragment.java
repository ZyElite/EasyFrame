/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.zy.ef.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.zy.ef.TitleBarActivity;


/**
 * A fragment that displays a WebView.
 * <p>
 * The WebView is automically paused or resumed when the Fragment is paused or resumed.
 */
public class WebViewFragment extends Fragment implements TitleBarActivity.OnBackPressedListener {

    protected static WebView mWebView;

    protected static boolean mIsWebViewAvailable;

    protected String mUrl;

    protected static FragmentActivity mContext;



    public WebViewFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(Uri.class.getName());
        }
        mContext = getActivity();

    }

    /**
     * Called to instantiate the view. Creates and returns the WebView.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mWebView != null) {
            mWebView.destroy();
        }
        mWebView = new WebView(getActivity());
        mWebView.setWebChromeClient(new WebChromeClient());
        //mWebView.addJavascriptInterface(new InJavaScriptLocalObj(), "androidphone");
        configureWebView(mWebView.getSettings());
        mIsWebViewAvailable = true;
        return mWebView;
    }

    protected void configureWebView(WebSettings settings) {
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        settings.setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        settings.setSupportZoom(true);//是否可以缩放，默认true
        settings.setBuiltInZoomControls(false);//是否显示缩放按钮，默认false
        settings.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        settings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        settings.setAppCacheEnabled(true);//是否使用缓存
        settings.setDomStorageEnabled(true);//DOM Storage
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url); //在当前的webview中跳转到新的url
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                WebViewFragment.this.onPageFinished(view, url);
            }
        });

        if (!TextUtils.isEmpty(mUrl)) {
            mWebView.loadUrl(mUrl);
        }
    }

    public void onPageFinished(WebView view, String url) {
        //   if (view.canGoBack()) {
        //  ((TitleBarActivity) getActivity()).setBackButton(null);
        //  } else {
        //  ((TitleBarActivity) getActivity()).setLeftButtonEmtpy();
        // }
    }

    /**
     * Called when the fragment is visible to the user and actively running. Resumes the WebView.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onPause() {
        super.onPause();
        ((TitleBarActivity) getActivity()).removeBackPressedListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mWebView.onPause();
        }
    }

    /**
     * Called when the fragment is no longer resumed. Pauses the WebView.
     */
    @Override
    public void onResume() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mWebView.onResume();
        }
        ((TitleBarActivity) getActivity()).addBackPressedListener(this);
        super.onResume();
    }

    /**
     * Called when the WebView has been detached from the fragment. The WebView is no longer
     * available after this time.
     */
    @Override
    public void onDestroyView() {
        ((TitleBarActivity) getActivity()).setLeftButtonEmtpy();
        mIsWebViewAvailable = false;
        super.onDestroyView();
    }

    /**
     * Called when the fragment is no longer in use. Destroys the internal state of the WebView.
     */
    @Override
    public void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }


    /**
     * Gets the WebView.
     */
    public static WebView getWebView() {
        return mIsWebViewAvailable ? mWebView : null;
    }

    public static Context getmContext() {
        return mContext;
    }



    @Override
    public boolean onBackPressed() {

        if (canGoBack()) {
            mWebView.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        return false;
    }

    private boolean canGoBack() {
        return mWebView.canGoBack();
    }


}