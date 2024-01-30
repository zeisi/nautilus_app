package com.verticalbargraphiclibrary;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.nautilus.omni.util.Constants;
import java.text.DecimalFormat;

@SuppressLint({"NewApi"})
public class CircleDisplay extends View implements GestureDetector.OnGestureListener, Animator.AnimatorListener {
    public static final int ALPHA_DIM = -1;
    public static int CIRCLE_GRAPHIC_ANIM_DURATION = 2000;
    private static final String LOG_TAG = "CircleDisplay";
    public static final int PAINT_ARC = 2;
    public static final int PAINT_INNER = 3;
    public static final int PAINT_TEXT = 1;
    private float mAngle = 0.0f;
    private AnimationListener mAnimationListener;
    private Paint mArcPaint;
    private boolean mBoxSetup = false;
    private RectF mCircleBox = new RectF();
    int mColor;
    private int mCustomDimColor = -1;
    private String[] mCustomText = null;
    private int mDimAlpha = 80;
    private ObjectAnimator mDrawAnimator;
    private boolean mDrawInner = true;
    private boolean mDrawText = true;
    private DecimalFormat mFormatValue = new DecimalFormat("###,###,###,###");
    private GestureDetector mGestureDetector;
    private Paint mInnerCirclePaint;
    private SelectionListener mListener;
    private float mMaxValue = 0.0f;
    private float mPhase = 0.0f;
    private float mStartAngle = 270.0f;
    private float mStepSize = 1.0f;
    private Paint mTextPaint;
    private boolean mTouchEnabled = true;
    private String mUnit = "%";
    private float mValue = 0.0f;
    private float mValueWidthPercent = 50.0f;

    public interface AnimationListener {
        void endAnimation();

        void startAnimation();
    }

    public interface SelectionListener {
        void onSelectionUpdate(float f, float f2);

        void onValueSelected(float f, float f2);
    }

    public CircleDisplay(Context context) {
        super(context);
        init();
    }

    public CircleDisplay(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleDisplay(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.mBoxSetup = false;
        this.mArcPaint = new Paint(1);
        this.mArcPaint.setStyle(Paint.Style.FILL);
        this.mArcPaint.setColor(Color.rgb(192, 255, 140));
        this.mInnerCirclePaint = new Paint(1);
        this.mInnerCirclePaint.setStyle(Paint.Style.FILL);
        this.mInnerCirclePaint.setColor(-1);
        this.mTextPaint = new Paint(1);
        this.mTextPaint.setStyle(Paint.Style.STROKE);
        this.mTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mTextPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.mTextPaint.setTextSize(Utils.convertDpToPixel(getResources(), 24.0f));
        this.mDrawAnimator = ObjectAnimator.ofFloat(this, "phase", new float[]{this.mPhase, 1.0f}).setDuration(3000);
        this.mDrawAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        this.mDrawAnimator.addListener(this);
        this.mGestureDetector = new GestureDetector(getContext(), this);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.mBoxSetup) {
            this.mBoxSetup = true;
            setupBox();
        }
        drawWholeCircle(canvas);
        drawValue(canvas);
        if (this.mDrawInner) {
            drawInnerCircle(canvas);
        }
        if (!this.mDrawText) {
            return;
        }
        if (this.mCustomText != null) {
            drawCustomText(canvas);
        } else {
            drawText(canvas);
        }
    }

    private void drawText(Canvas c) {
        c.drawText(this.mFormatValue.format((double) (this.mValue * this.mPhase)) + Constants.EMPTY_SPACE + this.mUnit, (float) (getWidth() / 2), ((float) (getHeight() / 2)) + this.mTextPaint.descent(), this.mTextPaint);
    }

    private void drawCustomText(Canvas c) {
        int index = (int) ((this.mValue * this.mPhase) / this.mStepSize);
        if (index < this.mCustomText.length) {
            c.drawText(this.mCustomText[index], (float) (getWidth() / 2), ((float) (getHeight() / 2)) + this.mTextPaint.descent(), this.mTextPaint);
        } else {
            Log.e(LOG_TAG, "Custom text array not long enough.");
        }
    }

    private void drawWholeCircle(Canvas c) {
        if (this.mCustomDimColor == -1) {
            this.mArcPaint.setAlpha(this.mDimAlpha);
        } else {
            this.mArcPaint.setColor(this.mCustomDimColor);
        }
        c.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), getRadius(), this.mArcPaint);
        this.mArcPaint.setColor(this.mColor);
    }

    public void setmCustomDimColor(int color) {
        this.mCustomDimColor = color;
    }

    private void drawInnerCircle(Canvas c) {
        c.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), (getRadius() / 100.0f) * (100.0f - this.mValueWidthPercent), this.mInnerCirclePaint);
    }

    private void drawValue(Canvas c) {
        this.mArcPaint.setAlpha(255);
        Canvas canvas = c;
        canvas.drawArc(this.mCircleBox, this.mStartAngle, this.mAngle * this.mPhase, true, this.mArcPaint);
    }

    private void setupBox() {
        int width = getWidth();
        int height = getHeight();
        float diameter = getDiameter();
        this.mCircleBox = new RectF(((float) (width / 2)) - (diameter / 2.0f), ((float) (height / 2)) - (diameter / 2.0f), ((float) (width / 2)) + (diameter / 2.0f), ((float) (height / 2)) + (diameter / 2.0f));
    }

    public void showValue(float toShow, float total, boolean animated) {
        this.mAngle = calcAngle((toShow / total) * 100.0f);
        this.mValue = toShow;
        this.mMaxValue = total;
        if (animated) {
            startAnim();
            return;
        }
        this.mPhase = 1.0f;
        invalidate();
    }

    public void setUnit(String unit) {
        this.mUnit = unit;
    }

    public float getValue() {
        return this.mValue;
    }

    public void startAnim() {
        this.mPhase = 0.0f;
        this.mDrawAnimator.start();
    }

    public void setAnimDuration(int durationmillis) {
        this.mDrawAnimator.setDuration((long) durationmillis);
    }

    public float getDiameter() {
        return (float) Math.min(getWidth(), getHeight());
    }

    public float getRadius() {
        return getDiameter() / 2.0f;
    }

    private float calcAngle(float percent) {
        return (percent / 100.0f) * 360.0f;
    }

    public void setStartAngle(float angle) {
        this.mStartAngle = angle;
    }

    public float getPhase() {
        return this.mPhase;
    }

    public void setPhase(float phase) {
        this.mPhase = phase;
        invalidate();
    }

    public void setDrawInnerCircle(boolean enabled) {
        this.mDrawInner = enabled;
    }

    public boolean isDrawInnerCircleEnabled() {
        return this.mDrawInner;
    }

    public void setDrawText(boolean enabled) {
        this.mDrawText = enabled;
    }

    public boolean isDrawTextEnabled() {
        return this.mDrawText;
    }

    public void setColor(int color) {
        this.mColor = color;
        this.mArcPaint.setColor(color);
    }

    public void setmAnimationListener(AnimationListener mAnimationListener2) {
        this.mAnimationListener = mAnimationListener2;
    }

    public void setTextSize(float size) {
        this.mTextPaint.setTextSize(Utils.convertDpToPixel(getResources(), size));
    }

    public void setValueWidthPercent(float percentFromTotalWidth) {
        this.mValueWidthPercent = percentFromTotalWidth;
    }

    public void setCustomText(String[] custom) {
        this.mCustomText = custom;
    }

    public void setFormatDigits(int digits) {
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < digits; i++) {
            if (i == 0) {
                b.append(".");
            }
            b.append("0");
        }
        this.mFormatValue = new DecimalFormat("###,###,###,##0" + b.toString());
    }

    public void setDimAlpha(int alpha) {
        this.mDimAlpha = alpha;
    }

    public void setPaint(int which, Paint p) {
        switch (which) {
            case 1:
                this.mTextPaint = p;
                return;
            case 2:
                this.mArcPaint = p;
                return;
            case 3:
                this.mInnerCirclePaint = p;
                return;
            default:
                return;
        }
    }

    public void setStepSize(float stepsize) {
        this.mStepSize = stepsize;
    }

    public float getStepSize() {
        return this.mStepSize;
    }

    public PointF getCenter() {
        return new PointF((float) (getWidth() / 2), (float) (getHeight() / 2));
    }

    public void setTouchEnabled(boolean enabled) {
        this.mTouchEnabled = enabled;
    }

    public boolean isTouchEnabled() {
        return this.mTouchEnabled;
    }

    public void setSelectionListener(SelectionListener l) {
        this.mListener = l;
    }

    public boolean onTouchEvent(MotionEvent e) {
        if (!this.mTouchEnabled) {
            return super.onTouchEvent(e);
        }
        if (this.mListener == null) {
            Log.w(LOG_TAG, "No SelectionListener specified. Use setSelectionListener(...) to set a listener for callbacks when selecting values.");
        }
        if (this.mGestureDetector.onTouchEvent(e)) {
            return true;
        }
        float x = e.getX();
        float y = e.getY();
        float distance = distanceToCenter(x, y);
        float r = getRadius();
        if (distance < r - ((this.mValueWidthPercent * r) / 100.0f) || distance >= r) {
            return true;
        }
        switch (e.getAction()) {
            case 1:
                if (this.mListener == null) {
                    return true;
                }
                this.mListener.onValueSelected(this.mValue, this.mMaxValue);
                return true;
            case 2:
                updateValue(x, y);
                invalidate();
                if (this.mListener == null) {
                    return true;
                }
                this.mListener.onSelectionUpdate(this.mValue, this.mMaxValue);
                return true;
            default:
                return true;
        }
    }

    private void updateValue(float x, float y) {
        float newVal;
        float angle = getAngleForPoint(x, y);
        float newVal2 = (this.mMaxValue * angle) / 360.0f;
        if (this.mStepSize == 0.0f) {
            this.mValue = newVal2;
            this.mAngle = angle;
            return;
        }
        float remainder = newVal2 % this.mStepSize;
        if (remainder <= this.mStepSize / 2.0f) {
            newVal = newVal2 - remainder;
        } else {
            newVal = (newVal2 - remainder) + this.mStepSize;
        }
        this.mAngle = getAngleForValue(newVal);
        this.mValue = newVal;
    }

    public boolean onSingleTapUp(MotionEvent e) {
        float distance = distanceToCenter(e.getX(), e.getY());
        float r = getRadius();
        if (distance < r - ((this.mValueWidthPercent * r) / 100.0f) || distance >= r) {
            return true;
        }
        updateValue(e.getX(), e.getY());
        invalidate();
        if (this.mListener == null) {
            return true;
        }
        this.mListener.onValueSelected(this.mValue, this.mMaxValue);
        return true;
    }

    public float getAngleForPoint(float x, float y) {
        PointF c = getCenter();
        double tx = (double) (x - c.x);
        double ty = (double) (y - c.y);
        float angle = (float) Math.toDegrees(Math.acos(ty / Math.sqrt((tx * tx) + (ty * ty))));
        if (x > c.x) {
            angle = 360.0f - angle;
        }
        float angle2 = angle + 180.0f;
        if (angle2 > 360.0f) {
            return angle2 - 360.0f;
        }
        return angle2;
    }

    public float getAngleForValue(float value) {
        return (value / this.mMaxValue) * 360.0f;
    }

    public float getValueForAngle(float angle) {
        return (angle / 360.0f) * this.mMaxValue;
    }

    public float distanceToCenter(float x, float y) {
        float xDist;
        float yDist;
        PointF c = getCenter();
        if (x > c.x) {
            xDist = x - c.x;
        } else {
            xDist = c.x - x;
        }
        if (y > c.y) {
            yDist = y - c.y;
        } else {
            yDist = c.y - y;
        }
        return (float) Math.sqrt(Math.pow((double) xDist, 2.0d) + Math.pow((double) yDist, 2.0d));
    }

    public void onAnimationStart(Animator animator) {
        if (this.mAnimationListener != null) {
            this.mAnimationListener.startAnimation();
        }
    }

    public void onAnimationEnd(Animator animator) {
        if (this.mAnimationListener != null) {
            this.mAnimationListener.endAnimation();
        }
    }

    public void onAnimationCancel(Animator animator) {
    }

    public void onAnimationRepeat(Animator animator) {
    }

    public static abstract class Utils {
        public static float convertDpToPixel(Resources r, float dp) {
            return dp * (((float) r.getDisplayMetrics().densityDpi) / 160.0f);
        }
    }

    public boolean onDown(MotionEvent e) {
        return false;
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    public void onLongPress(MotionEvent e) {
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    public void onShowPress(MotionEvent e) {
    }

    public static void initCustomCircleDisplayGraphic(Context context, CircleDisplay circleGraphicDisplay, int graphicColor, int innerCircleColor, int textColor, int textSize, int graphicWidthPercentage, int dimColor, AnimationListener animationListener) {
        circleGraphicDisplay.setmCustomDimColor(dimColor);
        circleGraphicDisplay.setTouchEnabled(false);
        circleGraphicDisplay.setColor(graphicColor);
        circleGraphicDisplay.setAnimDuration(CIRCLE_GRAPHIC_ANIM_DURATION);
        circleGraphicDisplay.setValueWidthPercent((float) graphicWidthPercentage);
        circleGraphicDisplay.setDrawText(true);
        circleGraphicDisplay.setUnit(context.getString(R.string.circle_percentage));
        circleGraphicDisplay.setmAnimationListener(animationListener);
        Paint innerCirclePaint = new Paint(1);
        innerCirclePaint.setStyle(Paint.Style.FILL);
        innerCirclePaint.setColor(innerCircleColor);
        circleGraphicDisplay.setPaint(3, innerCirclePaint);
        Paint textPaint = new Paint(1);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bold.ttf"));
        textPaint.setColor(textColor);
        textPaint.setTextSize(Utils.convertDpToPixel(context.getResources(), (float) textSize));
        circleGraphicDisplay.setPaint(1, textPaint);
    }
}
