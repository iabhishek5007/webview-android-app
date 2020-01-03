package com.ontrack.bikex;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Homepage extends AppCompatActivity {

    private static final String TAG = "Homepage";
    WebView webView;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_homepage);

        getSupportActionBar().hide();

        webView = (WebView) findViewById(R.id.bikexwebview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDatabaseEnabled(true);


        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //Users will be notified in case there's an error (i.e. no internet connection)
                Toast.makeText(Homepage.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }

            public void onPageFinished(WebView view, String url) {
                CookieSyncManager.getInstance().sync();
            }
        });
        webView.loadUrl("https://bikex.in/");

    }


    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed");

        if (doubleBackToExitPressedOnce) {
            Log.i(TAG, "double click");
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.bikex)
                    .setTitle("Press Exit To Close App:")
                    .setMessage("Are you sure to exit?")
                    .setPositiveButton("Exit",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    finish();
                                }

                            }).setNegativeButton("No", null).show();
            return;
        } else {
            Log.i(TAG, "single click");
            if (webView.canGoBack()) {
                Log.i(TAG, "canGoBack");
                webView.goBack();
            } else {
                Log.i(TAG, "nothing to canGoBack");
            }
        }

        this.doubleBackToExitPressedOnce = true;
        if (getApplicationContext() == null) {
            return;
        } else {
        //    Toast.makeText(this, "Please click BACK again to exit",
               //     Toast.LENGTH_SHORT).show();
        }
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }



}
