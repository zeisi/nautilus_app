package com.nautilus.omni.appmodules.settings.mainsection.view.about;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BaseActivity;

public class AboutActivity extends BaseActivity {
    public static final String CONTENT = "CONTENT";
    public static final String TITLE = "TITLE";
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private WebView mWebView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_about);
        this.mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        this.mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        String title = getIntent().getStringExtra("TITLE");
        String content = getIntent().getStringExtra(CONTENT);
        this.mToolbarTitle.setText(title);
        this.mWebView = (WebView) findViewById(R.id.webView);
        this.mWebView.loadUrl(content);
        this.mWebView.setBackgroundColor(0);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                if (!isFinishing()) {
                    onBackPressed();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
