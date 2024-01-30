package com.myfitnesspal.android.sdk;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.myfitnesspal.android.api.R;
import com.myfitnesspal.shared.constants.Constants;
import com.myfitnesspal.shared.utils.Ln;
import com.myfitnesspal.shared.utils.UriUtils;

public class MfpDialog extends Dialog {
    static final FrameLayout.LayoutParams FILL = new FrameLayout.LayoutParams(-1, -1);
    private static final String MFP_WEB_BASE_URL = "https://www.myfitnesspal.com/oauth2";
    /* access modifiers changed from: private */
    public MfpAuthListener authListener;
    /* access modifiers changed from: private */
    public FrameLayout content;
    /* access modifiers changed from: private */
    public ImageView crossImage;
    /* access modifiers changed from: private */
    public String redirectUrl;
    /* access modifiers changed from: private */
    public ProgressDialog spinner;
    private String url;
    /* access modifiers changed from: private */
    public WebView webView;

    public MfpDialog(Context context, String action, AuthorizeRequestData requestData, Bundle params, MfpAuthListener authListener2) {
        super(context, 16973840);
        params.putAll(requestData.asBundle());
        this.url = String.format("%s/%s?%s", new Object[]{MFP_WEB_BASE_URL, action, UriUtils.urlEncode(params)});
        this.redirectUrl = requestData.getRedirectUri();
        this.authListener = authListener2;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.spinner = new ProgressDialog(getContext());
        this.spinner.requestWindowFeature(1);
        this.spinner.setMessage("Loading...");
        requestWindowFeature(1);
        this.content = new FrameLayout(getContext());
        createCrossImage();
        setUpWebView(this.crossImage.getDrawable().getIntrinsicWidth() / 2);
        this.content.addView(this.crossImage, new ViewGroup.LayoutParams(-2, -2));
        addContentView(this.content, new ViewGroup.LayoutParams(-1, -1));
    }

    public void onBackPressed() {
        super.onBackPressed();
        this.authListener.onCancel((Bundle) null);
    }

    private void createCrossImage() {
        this.crossImage = new ImageView(getContext());
        this.crossImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MfpDialog.this.authListener.onCancel((Bundle) null);
                MfpDialog.this.dismiss();
            }
        });
        this.crossImage.setImageDrawable(getContext().getResources().getDrawable(R.drawable.close));
        this.crossImage.setVisibility(4);
    }

    private void setUpWebView(int margin) {
        LinearLayout webViewContainer = new LinearLayout(getContext());
        this.webView = new WebView(getContext());
        this.webView.setVerticalScrollBarEnabled(false);
        this.webView.setHorizontalScrollBarEnabled(false);
        this.webView.setWebViewClient(new MfpWebViewClient());
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.loadUrl(this.url);
        this.webView.setLayoutParams(FILL);
        this.webView.setVisibility(4);
        this.webView.getSettings().setSavePassword(false);
        webViewContainer.setPadding(margin, margin, margin, margin);
        webViewContainer.addView(this.webView);
        this.content.addView(webViewContainer);
    }

    private class MfpWebViewClient extends WebViewClient {
        private MfpWebViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Ln.d("Redirect URL: " + url, new Object[0]);
            if (url.startsWith(MfpDialog.this.redirectUrl)) {
                Bundle params = UriUtils.getQueryParams(url);
                String error = params.getString(Constants.Params.ERROR);
                if (error == null) {
                    MfpDialog.this.authListener.onComplete(params);
                } else if (error.equals(Constants.Errors.ACCESS_DENIED) || error.equals("OAuthAccessDeniedException")) {
                    MfpDialog.this.authListener.onCancel(params);
                } else {
                    MfpDialog.this.authListener.onMfpError(new MfpAuthError(error));
                }
                MfpDialog.this.dismiss();
                return true;
            } else if (url.startsWith(MfpDialog.MFP_WEB_BASE_URL)) {
                return false;
            } else {
                MfpDialog.this.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                return true;
            }
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            MfpDialog.this.authListener.onError(new MfpWebError(description, errorCode, failingUrl));
            MfpDialog.this.dismiss();
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Ln.d("Webview loading URL: " + url, new Object[0]);
            super.onPageStarted(view, url, favicon);
            MfpDialog.this.spinner.show();
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            MfpDialog.this.spinner.dismiss();
            MfpDialog.this.content.setBackgroundColor(0);
            MfpDialog.this.webView.setVisibility(0);
            MfpDialog.this.crossImage.setVisibility(0);
        }
    }
}
