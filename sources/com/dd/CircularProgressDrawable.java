package com.dd;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

class CircularProgressDrawable extends Drawable {
    private Paint mPaint;
    private Path mPath;
    private RectF mRectF;
    private int mSize;
    private float mStartAngle = -90.0f;
    private int mStrokeColor;
    private int mStrokeWidth;
    private float mSweepAngle = 0.0f;

    public CircularProgressDrawable(int size, int strokeWidth, int strokeColor) {
        this.mSize = size;
        this.mStrokeWidth = strokeWidth;
        this.mStrokeColor = strokeColor;
    }

    public void setSweepAngle(float sweepAngle) {
        this.mSweepAngle = sweepAngle;
    }

    public int getSize() {
        return this.mSize;
    }

    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        if (this.mPath == null) {
            this.mPath = new Path();
        }
        this.mPath.reset();
        this.mPath.addArc(getRect(), this.mStartAngle, this.mSweepAngle);
        this.mPath.offset((float) bounds.left, (float) bounds.top);
        canvas.drawPath(this.mPath, createPaint());
    }

    public void setAlpha(int alpha) {
    }

    public void setColorFilter(ColorFilter cf) {
    }

    public int getOpacity() {
        return 1;
    }

    private RectF getRect() {
        if (this.mRectF == null) {
            int index = this.mStrokeWidth / 2;
            this.mRectF = new RectF((float) index, (float) index, (float) (getSize() - index), (float) (getSize() - index));
        }
        return this.mRectF;
    }

    private Paint createPaint() {
        if (this.mPaint == null) {
            this.mPaint = new Paint();
            this.mPaint.setAntiAlias(true);
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setStrokeWidth((float) this.mStrokeWidth);
            this.mPaint.setColor(this.mStrokeColor);
        }
        return this.mPaint;
    }
}
