package com.example.skincancerdetectiion;

import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class webviewActivity extends AppCompatActivity {
    WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        myWebView = (WebView) findViewById(R.id.webview);

        String a = "";
        String b = "";
        a = getIntent().getStringExtra("viewMore");
        b=getIntent().getStringExtra("hashmapString");
        if (getIntent() != null) {

            if (a != null) {
                web(a);
            } else {
                web(b);
            }
        }


    }

    public void web(String uri) {

        myWebView.loadUrl(uri);

        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // Handle page load completion event
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                // Handle error event
            }
        });

    }
}