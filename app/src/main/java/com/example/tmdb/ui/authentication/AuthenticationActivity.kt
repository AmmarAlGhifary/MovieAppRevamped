package com.example.tmdb.ui.authentication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.tmdb.databinding.ActivityAuthenticationBinding
import com.example.tmdb.util.extractRequestToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {

    private val viewModel: AuthenticationViewModel by viewModels()
    private lateinit var binding: ActivityAuthenticationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val redirectUrl = "tmdb://approved"
        viewModel.fetchRequestToken(redirectUrl)

        viewModel.requestToken.observe(this) { token ->
            Log.d(this@AuthenticationActivity.toString(), "Request token received: $token")
            if (token.isNotEmpty()) {
                binding.wvAuthentication.apply {
                    settings.javaScriptEnabled = true
                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            url: String?
                        ): Boolean {
                            Log.d("AuthActivity", "URL loading: $url")
                            if (url?.startsWith("tmdb://") == true) {
                                Log.d("AuthActivity", "Attempting to start activity for URL: $url")
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                startActivity(intent)
                                return true
                            }
                            return false
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            Log.d("AuthActivity", "Page finished loading: $url")
                        }
                    }
                    loadUrl("https://www.themoviedb.org/authenticate/$token?redirect_to=tmdb://approved")
                }

            }
        }

        viewModel.authUiState.observe(this) { uiState ->
            if (uiState.isSuccess) {
                Log.d(this@AuthenticationActivity.toString(), "Authentication successful")
                finish() // Close the activity when authentication is successful
            } else if (uiState.isError) {
                Log.e(
                    this@AuthenticationActivity.toString(),
                    "Authentication error: ${uiState.errorText}"
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("AuthActivity", "onNewIntent called with intent: $intent")
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        Log.d("AuthActivity", "handleIntent called with intent: $intent")
        intent?.data?.let { uri ->
            Log.d("AuthActivity", "URI received: $uri")
            if (uri.scheme == "tmdb" && uri.host == "approved") {
                val requestToken = uri.getQueryParameter("request_token")
                Log.d("AuthActivity", "Request token extracted: $requestToken")
                if (requestToken != null) {
                    viewModel.authenticate(requestToken)
                } else {
                    Log.e("AuthActivity", "Request token is null")
                }
            } else {
                Log.d("AuthActivity", "URI doesn't match expected scheme and host")
            }
        } ?: Log.e("AuthActivity", "Intent data is null")
    }
}
