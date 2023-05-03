package com.example.android;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WebActivity extends AppCompatActivity {

    WebView webView;
    String mUrl;
    private Map<String, String> httpHeaders = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
      //  getHtmlFromWeb();
        webView = (WebView)findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient(webView));
        String userAgent = String.format("%s [%s/%s]", webSettings.getUserAgentString(), "Android", BuildConfig.VERSION_NAME);
        webView.getSettings().setUserAgentString(userAgent);
        webView.loadUrl("https://kiou.ru/skolko-na-samom-dele-voditelej-ne-pristegivaetsya-v-avto/");



        webView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
              //  WebView.HitTestResult hr = ((WebView)v).getHitTestResult();
              //  System.out.println("setOnTouchListener: " + hr.getExtra() + "  -  " + hr.getType());

                return false;
            }
        });

    }

    private class MyWebViewClient extends WebViewClient {
        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
          //  System.out.println("onPageStarted: " + url);
        }

        @Override
        public void onPageFinished(WebView webView, String url) {
          //  System.out.println("onPageFinished: " + url);
          //  webView.stopLoading();
        }

        @Override
        public void onLoadResource(WebView view, String url) {
          //  System.out.println("onLoadResource: " + url);

        }

    }

    private class MyWebChromeClient extends WebChromeClient {

        private WebView mWebView;

        public MyWebChromeClient(WebView webView)
        {
            mWebView = webView;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
               // System.out.println("onProgressChanged: " + newProgress);
              //  mWebView.loadUrl("javascript:callFromActivity('some msg')");
            }
        }

    }

    private void getHtmlFromWeb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder stringBuilder = new StringBuilder();

                try {
                    Document doc = Jsoup.connect("https://kiou.ru/skolko-na-samom-dele-voditelej-ne-pristegivaetsya-v-avto/").get();
                    System.out.println(doc);
                    String title = doc.title();
                    Elements links = doc.select("a[href]");
                    stringBuilder.append(title).append("\n");
                    for (Element link : links) {
                        stringBuilder.append("\n").append("Link : ").append(link.attr("href")).append("\n").append("Text : ").append(link.text());
                    }
                } catch (IOException e) {
                    stringBuilder.append("Error : ").append(e.getMessage()).append("\n");
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                     //   textView.setText(stringBuilder.toString());
                      //  System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ "+stringBuilder.toString());
                    }
                });
            }
        }).start();
    }

}
