package com.dd;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.GradientDrawable;
import android.widget.TextView;
import com.nautilus.omni.model.dto.AwardType;

class MorphingAnimation {
    public static final int DURATION_INSTANT = 1;
    public static final int DURATION_NORMAL = 400;
    private StrokeGradientDrawable mDrawable;
    private int mDuration;
    private int mFromColor;
    private float mFromCornerRadius;
    private int mFromStrokeColor;
    /* access modifiers changed from: private */
    public int mFromWidth;
    /* access modifiers changed from: private */
    public OnAnimationEndListener mListener;
    /* access modifiers changed from: private */
    public float mPadding;
    private int mToColor;
    private float mToCornerRadius;
    private int mToStrokeColor;
    /* access modifiers changed from: private */
    public int mToWidth;
    /* access modifiers changed from: private */
    public TextView mView;

    public MorphingAnimation(TextView viewGroup, StrokeGradientDrawable drawable) {
        this.mView = viewGroup;
        this.mDrawable = drawable;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public void setListener(OnAnimationEndListener listener) {
        this.mListener = listener;
    }

    public void setFromWidth(int fromWidth) {
        this.mFromWidth = fromWidth;
    }

    public void setToWidth(int toWidth) {
        this.mToWidth = toWidth;
    }

    public void setFromColor(int fromColor) {
        this.mFromColor = fromColor;
    }

    public void setToColor(int toColor) {
        this.mToColor = toColor;
    }

    public void setFromStrokeColor(int fromStrokeColor) {
        this.mFromStrokeColor = fromStrokeColor;
    }

    public void setToStrokeColor(int toStrokeColor) {
        this.mToStrokeColor = toStrokeColor;
    }

    public void setFromCornerRadius(float fromCornerRadius) {
        this.mFromCornerRadius = fromCornerRadius;
    }

    public void setToCornerRadius(float toCornerRadius) {
        this.mToCornerRadius = toCornerRadius;
    }

    public void setPadding(float padding) {
        this.mPadding = padding;
    }

    public void start() {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(new int[]{this.mFromWidth, this.mToWidth});
        final GradientDrawable gradientDrawable = this.mDrawable.getGradientDrawable();
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                int leftOffset;
                int rightOffset;
                int padding;
                Integer value = (Integer) animation.getAnimatedValue();
                if (MorphingAnimation.this.mFromWidth > MorphingAnimation.this.mToWidth) {
                    leftOffset = (MorphingAnimation.this.mFromWidth - value.intValue()) / 2;
                    rightOffset = MorphingAnimation.this.mFromWidth - leftOffset;
                    padding = (int) (MorphingAnimation.this.mPadding * animation.getAnimatedFraction());
                } else {
                    leftOffset = (MorphingAnimation.this.mToWidth - value.intValue()) / 2;
                    rightOffset = MorphingAnimation.this.mToWidth - leftOffset;
                    padding = (int) (MorphingAnimation.this.mPadding - (MorphingAnimation.this.mPadding * animation.getAnimatedFraction()));
                }
                gradientDrawable.setBounds(leftOffset + padding, padding, rightOffset - padding, MorphingAnimation.this.mView.getHeight() - padding);
            }
        });
        ObjectAnimator bgColorAnimation = ObjectAnimator.ofInt(gradientDrawable, AwardType.COLOR, new int[]{this.mFromColor, this.mToColor});
        bgColorAnimation.setEvaluator(new ArgbEvaluator());
        ObjectAnimator strokeColorAnimation = ObjectAnimator.ofInt(this.mDrawable, "strokeColor", new int[]{this.mFromStrokeColor, this.mToStrokeColor});
        strokeColorAnimation.setEvaluator(new ArgbEvaluator());
        ObjectAnimator cornerAnimation = ObjectAnimator.ofFloat(gradientDrawable, "cornerRadius", new float[]{this.mFromCornerRadius, this.mToCornerRadius});
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration((long) this.mDuration);
        animatorSet.playTogether(new Animator[]{widthAnimation, bgColorAnimation, strokeColorAnimation, cornerAnimation});
        animatorSet.addListener(new Animator.AnimatorListener() {
            public void onAnimationStart(Animator animation) {
            }

            public void onAnimationEnd(Animator animation) {
                if (MorphingAnimation.this.mListener != null) {
                    MorphingAnimation.this.mListener.onAnimationEnd();
                }
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        });
        animatorSet.start();
    }
}
