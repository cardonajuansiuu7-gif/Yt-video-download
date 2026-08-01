package com.ytultra.wrapper

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val siteUrl = "https://www.ytultra.com/es/youtube-video-downloader/"

    private val customCss = """
        header, footer, .navbar, .ads, .ad-banner { display: none !important; }
        body { background-color: #121212 !important; color: #ffffff !important; }
        input, button { border-radius: 12px !important; }
        button, .btn { background-color: #ff3b30 !important; color: #ffffff !important; }
    """.trimIndent()

    private val injectCssJs = """
        (function() {
            var style = document.createElement('style');
            style.innerHTML = `$customCss`;
            document.head.appendChild(style);
        })();
    """.trimIndent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webView = findViewById<WebView>(R.id.webview)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                view.evaluateJavascript(injectCssJs, null)
            }
        }

        webView.loadUrl(siteUrl)
    }

    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        val webView = findViewById<WebView>(R.id.webview)
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
