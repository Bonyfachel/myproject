package com.example.vkapiexample;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class AuthActivity extends BaseActivity {

    private static final String VK_APP_ID = "7836150";
    public static final String LOG_TAG = "MainActivity";
    private String VK_URL = "https://oauth.vk.com/authorize?" +
                    "client_id=" + VK_APP_ID +
                    "&display=page" +
                    "&redirect_uri=https://oauth.vk.com/blank.html" +
                    "&scope=friends" +
                    "&response_type=token" +
                    "&v=5.130";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        if(hasToken()) {
            startHomeActivity();
            return;
        }

        findViewById(R.id.btn_vk_auth).setOnClickListener((v) -> {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("VK Auth")
                    .setView(R.layout.dialog_auth)
                    .create();

            dialog.show();

            EditText edit = dialog.findViewById(R.id.editText);
            edit.setFocusable(true);
            edit.requestFocus();

            WebView webView = dialog.findViewById(R.id.dialog_auth_web_view);
            webView.loadUrl(VK_URL);

            android.util.Log.i(LOG_TAG, VK_URL);

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);

                    if(url.contains("access_token")) {
                        String accessToken = url
                                .split("#")[1]
                                .split("&")[0]
                                .split("=")[1];

                        saveToken(accessToken);

                        android.util.Log.i(LOG_TAG, accessToken);

                        dialog.dismiss();

                        startHomeActivity();
                    }
                }
            });

        });
    }

    public void startHomeActivity() {
        Intent intent = new Intent(AuthActivity.this, HomeActivity.class);
        startActivity(intent);
    }

}