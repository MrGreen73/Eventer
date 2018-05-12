package com.ivan.eventer.view.Main.Search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.ivan.eventer.R;
import com.ivan.eventer.model.MyWebViewClient;


public class WebActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mWebView = findViewById(R.id.webView);
        // включаем поддержку JavaScript
        mWebView.getSettings().setJavaScriptEnabled(true);
        String typeSearch = getIntent().getStringExtra("TYPE");

        switch (typeSearch){

            case "A":
                mWebView.loadUrl("https://www.afisha.ru/");
                break;

            case "W":
                mWebView.loadUrl("https://yandex.ru/pogoda/");
                break;

            default:
                mWebView.loadUrl("https://www.afisha.ru/");

        }

        mWebView.setWebViewClient(new MyWebViewClient());
    }

    @Override
    public void onBackPressed() {

        if(mWebView.canGoBack()) {

            mWebView.goBack();
            finish();

        } else {

            super.onBackPressed();

        }

    }

}
