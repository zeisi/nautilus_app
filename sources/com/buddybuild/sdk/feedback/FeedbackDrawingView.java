package com.buddybuild.sdk.feedback;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import com.buddybuild.sdk.R;

public class FeedbackDrawingView extends View {
    private static final long DEMO_ANIMATION_DURATION = 150;
    private static final int FINGER_RADIUS = 40;
    /* access modifiers changed from: private */
    public float currX = 80.0f;
    /* access modifiers changed from: private */
    public float currY = 80.0f;
    private boolean hasSeenDemo = false;
    /* access modifiers changed from: private */
    public boolean isDemoing = false;
    private boolean isDrawing = false;
    private final Activity mActivity;
    private final Bitmap mBitmap;
    private final Canvas mCanvas;
    /* access modifiers changed from: private */
    public final Handler mDemoHandler = new Handler();
    private Runnable mDemoRunnable;
    private final Drawable mFinger;
    /* access modifiers changed from: private */
    public float mMaxDemoX;
    /* access modifiers changed from: private */
    public float mMaxDemoY;
    private final Paint mPaint;
    private final Drawable mRect;
    private float startX = 80.0f;
    private float startY = 80.0f;

    public FeedbackDrawingView(Activity activity, Bitmap bitmap) {
        super(activity);
        this.mActivity = activity;
        this.mBitmap = bitmap;
        this.mRect = activity.getResources().getDrawable(R.drawable.bb_rectangle_feedback);
        this.mFinger = activity.getResources().getDrawable(R.drawable.bb_feedback_demo_finger);
        this.mPaint = new Paint(2);
        this.mCanvas = new Canvas();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (!this.hasSeenDemo) {
            this.mMaxDemoX = (((float) getWidth()) * 0.75f) - this.startX;
            this.mMaxDemoY = (((float) getHeight()) * 0.75f) - this.startY;
            this.mDemoRunnable = new Runnable() {
                private float xStep = (FeedbackDrawingView.this.mMaxDemoX / 150.0f);
                private float yStep = (FeedbackDrawingView.this.mMaxDemoY / 150.0f);

                public void run() {
                    boolean unused = FeedbackDrawingView.this.isDemoing = true;
                    float unused2 = FeedbackDrawingView.this.currX = FeedbackDrawingView.this.currX + this.xStep;
                    float unused3 = FeedbackDrawingView.this.currY = FeedbackDrawingView.this.currY + this.yStep;
                    if (FeedbackDrawingView.this.currX < FeedbackDrawingView.this.mMaxDemoX) {
                        FeedbackDrawingView.this.invalidate();
                        FeedbackDrawingView.this.mDemoHandler.postDelayed(this, 16);
                    }
                }
            };
            startDemo();
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int height;
        int width;
        super.onDraw(canvas);
        float ratio = ((float) this.mBitmap.getWidth()) / ((float) this.mBitmap.getHeight());
        if (ratio > 1.0f) {
            width = getWidth();
            height = (int) (((float) width) / ratio);
        } else {
            height = getHeight();
            width = (int) (((float) height) * ratio);
        }
        canvas.drawBitmap(this.mBitmap, (Rect) null, new Rect(0, 0, width, height), this.mPaint);
        if (this.isDrawing || this.isDemoing) {
            onDrawRectangle(canvas);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        this.currX = event.getX();
        this.currY = event.getY();
        onTouchEventRectangle(event);
        return true;
    }

    private void onDrawRectangle(Canvas canvas) {
        drawRectangle(canvas);
    }

    private void onTouchEventRectangle(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                stopDemo();
                this.isDrawing = true;
                this.startX = this.currX;
                this.startY = this.currY;
                invalidate();
                return;
            case 1:
                drawRectangle(this.mCanvas);
                new FeedbackDialog(this.mActivity, FeedbackUtils.getViewScreenshot(this)).show();
                invalidate();
                return;
            case 2:
                invalidate();
                return;
            default:
                return;
        }
    }

    private void drawRectangle(Canvas canvas) {
        int right = (int) (this.startX > this.currX ? this.startX : this.currX);
        int left = (int) (this.startX > this.currX ? this.currX : this.startX);
        int bottom = (int) (this.startY > this.currY ? this.startY : this.currY);
        this.mRect.setBounds(new Rect(left, (int) (this.startY > this.currY ? this.currY : this.startY), right, bottom));
        this.mRect.draw(canvas);
        if (this.isDemoing) {
            this.mFinger.setBounds(right - 40, bottom - 40, right + 40, bottom + 40);
            this.mFinger.draw(canvas);
        }
    }

    private void startDemo() {
        if (!this.hasSeenDemo) {
            this.hasSeenDemo = true;
            this.mDemoHandler.removeCallbacks(this.mDemoRunnable);
            this.mDemoHandler.postDelayed(this.mDemoRunnable, 1500);
        }
    }

    private void stopDemo() {
        this.isDemoing = false;
        this.mDemoHandler.removeCallbacks(this.mDemoRunnable);
    }
}
