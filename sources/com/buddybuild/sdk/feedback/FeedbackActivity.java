package com.buddybuild.sdk.feedback;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import com.buddybuild.sdk.R;
import com.buddybuild.sdk.buildinfo.BuildInfoActivity;
import java.lang.ref.WeakReference;

public class FeedbackActivity extends Activity {
    /* access modifiers changed from: private */
    public FeedbackDrawingView mDrawingView;

    /* access modifiers changed from: protected */
    @TargetApi(12)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bitmap bitmap = FeedbackUtils.getIntentFeedbackScreenshot(new WeakReference(this));
        if (bitmap.getWidth() > bitmap.getHeight()) {
            setRequestedOrientation(0);
        } else {
            setRequestedOrientation(1);
        }
        requestWindowFeature(1);
        ViewGroup feedbackLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.bb_feedback_activity, (ViewGroup) null);
        this.mDrawingView = new FeedbackDrawingView(this, bitmap);
        feedbackLayout.addView(this.mDrawingView);
        ((TextView) feedbackLayout.findViewById(R.id.bb_cancel_text_view)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FeedbackActivity.this.mDrawingView.animate().setInterpolator(new AccelerateDecelerateInterpolator()).scaleX(1.0f).scaleY(1.0f).translationY(0.0f).setListener(new Animator.AnimatorListener() {
                    public void onAnimationStart(Animator animator) {
                    }

                    public void onAnimationEnd(Animator animation) {
                        FeedbackActivity.this.finish();
                    }

                    public void onAnimationCancel(Animator animator) {
                    }

                    public void onAnimationRepeat(Animator animator) {
                    }
                });
            }
        });
        ((Button) feedbackLayout.findViewById(R.id.bb_info_button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FeedbackActivity.this.startActivity(new Intent(FeedbackActivity.this, BuildInfoActivity.class));
            }
        });
        setContentView(feedbackLayout);
        animateFeedbackDrawingViewToPosition(feedbackLayout, this.mDrawingView);
    }

    @TargetApi(12)
    public void finish() {
        super.finish();
        ScreenshotCache.instance().evictAll();
    }

    public void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);
    }

    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @TargetApi(12)
    private void animateFeedbackDrawingViewToPosition(final ViewGroup rootLayout, final FeedbackDrawingView drawingView) {
        final ViewTreeObserver observer = rootLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (observer.isAlive()) {
                    if (Build.VERSION.SDK_INT >= 16) {
                        observer.removeOnGlobalLayoutListener(this);
                    } else {
                        observer.removeGlobalOnLayoutListener(this);
                    }
                }
                int topBarHeight = rootLayout.findViewById(R.id.bb_feedback_top_bar).getHeight();
                int bottomBarHeight = rootLayout.findViewById(R.id.bb_feedback_bottom_bar).getHeight();
                float scale = 1.0f - (((float) (topBarHeight + bottomBarHeight)) / ((float) rootLayout.getHeight()));
                drawingView.animate().setInterpolator(new AccelerateDecelerateInterpolator()).setStartDelay(300).scaleX(scale).scaleY(scale).translationY((float) ((topBarHeight - bottomBarHeight) / 2));
            }
        });
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(1);
    }
}
