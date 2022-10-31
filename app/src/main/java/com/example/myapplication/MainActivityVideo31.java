package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.webkit.WebViewAssetLoader;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivityVideo31 extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private WebView mWebView;
    //Embeded Youtube Video Address
    private final String VideoEmbededAdress = "<iframe width=\"335\" height=\"260\" src=\"https://www.youtube.com/embed/TxNjET-L-IA\" title=\"[食不相瞞#10]香酥薄脆的法式杏仁瓦片，瓦片餅乾食譜與作法(法式杏仁瓦片餅乾/Tuiles aux amandes recipe)\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>";
    private final String mimeType = "text/html";
    private final String encoding = "UTF-8";//"base64";
    private String USERAGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_video31);


        mWebView = (WebView) findViewById(R.id.youtubeView);

        WebViewAssetLoader assetLoader = new WebViewAssetLoader.Builder()
                .addPathHandler("/assets/", new WebViewAssetLoader.AssetsPathHandler(this))
                .build();

        mWebView.setWebViewClient(new WebViewClient() {
            private WebView view;
            private WebResourceRequest request;

            public WebResourceResponse shouldInterceptRequest(WebView view,
                                                              WebResourceRequest request) {
                Log.d(TAG, "shouldOverrideUrlLoading: Url = [" + request.getUrl()+"]");
                this.view = view;
                this.request = request;
                return assetLoader.shouldInterceptRequest(request.getUrl());
            }

        });

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAllowContentAccess(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        mWebView.getSettings().setUserAgentString(USERAGENT);//Important to auto play video
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(VideoEmbededAdress);
        mWebView.loadDataWithBaseURL("", VideoEmbededAdress, mimeType, encoding, "");
    }
}