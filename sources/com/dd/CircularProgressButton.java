package com.dd;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.View;
import android.widget.Button;
import com.dd.circular.progress.button.R;

public class CircularProgressButton extends Button {
    public static final int ERROR_STATE_PROGRESS = -1;
    public static final int IDLE_STATE_PROGRESS = 0;
    public static final int INDETERMINATE_STATE_PROGRESS = 50;
    public static final int SUCCESS_STATE_PROGRESS = 100;
    private StrokeGradientDrawable background;
    private CircularAnimatedDrawable mAnimatedDrawable;
    private int mColorIndicator;
    private int mColorIndicatorBackground;
    private int mColorProgress;
    private ColorStateList mCompleteColorState;
    private StateListDrawable mCompleteStateDrawable;
    private OnAnimationEndListener mCompleteStateListener = new OnAnimationEndListener() {
        public void onAnimationEnd() {
            if (CircularProgressButton.this.mIconComplete != 0) {
                CircularProgressButton.this.setText((CharSequence) null);
                CircularProgressButton.this.setIcon(CircularProgressButton.this.mIconComplete);
            } else {
                CircularProgressButton.this.setText(CircularProgressButton.this.mCompleteText);
            }
            boolean unused = CircularProgressButton.this.mMorphingInProgress = false;
            State unused2 = CircularProgressButton.this.mState = State.COMPLETE;
            CircularProgressButton.this.mStateManager.checkState(CircularProgressButton.this);
        }
    };
    /* access modifiers changed from: private */
    public String mCompleteText;
    private boolean mConfigurationChanged;
    private float mCornerRadius;
    private ColorStateList mErrorColorState;
    private StateListDrawable mErrorStateDrawable;
    private OnAnimationEndListener mErrorStateListener = new OnAnimationEndListener() {
        public void onAnimationEnd() {
            if (CircularProgressButton.this.mIconError != 0) {
                CircularProgressButton.this.setText((CharSequence) null);
                CircularProgressButton.this.setIcon(CircularProgressButton.this.mIconError);
            } else {
                CircularProgressButton.this.setText(CircularProgressButton.this.mErrorText);
            }
            boolean unused = CircularProgressButton.this.mMorphingInProgress = false;
            State unused2 = CircularProgressButton.this.mState = State.ERROR;
            CircularProgressButton.this.mStateManager.checkState(CircularProgressButton.this);
        }
    };
    /* access modifiers changed from: private */
    public String mErrorText;
    /* access modifiers changed from: private */
    public int mIconComplete;
    /* access modifiers changed from: private */
    public int mIconError;
    private ColorStateList mIdleColorState;
    private StateListDrawable mIdleStateDrawable;
    private OnAnimationEndListener mIdleStateListener = new OnAnimationEndListener() {
        public void onAnimationEnd() {
            CircularProgressButton.this.removeIcon();
            CircularProgressButton.this.setText(CircularProgressButton.this.mIdleText);
            boolean unused = CircularProgressButton.this.mMorphingInProgress = false;
            State unused2 = CircularProgressButton.this.mState = State.IDLE;
            CircularProgressButton.this.mStateManager.checkState(CircularProgressButton.this);
        }
    };
    /* access modifiers changed from: private */
    public String mIdleText;
    private boolean mIndeterminateProgressMode;
    private int mMaxProgress;
    /* access modifiers changed from: private */
    public boolean mMorphingInProgress;
    private int mPaddingProgress;
    private int mProgress;
    private CircularProgressDrawable mProgressDrawable;
    private OnAnimationEndListener mProgressStateListener = new OnAnimationEndListener() {
        public void onAnimationEnd() {
            boolean unused = CircularProgressButton.this.mMorphingInProgress = false;
            State unused2 = CircularProgressButton.this.mState = State.PROGRESS;
            CircularProgressButton.this.mStateManager.checkState(CircularProgressButton.this);
        }
    };
    private String mProgressText;
    /* access modifiers changed from: private */
    public State mState;
    /* access modifiers changed from: private */
    public StateManager mStateManager;
    private int mStrokeWidth;

    private enum State {
        PROGRESS,
        IDLE,
        COMPLETE,
        ERROR
    }

    public CircularProgressButton(Context context) {
        super(context);
        init(context, (AttributeSet) null);
    }

    public CircularProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircularProgressButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.mStrokeWidth = (int) getContext().getResources().getDimension(R.dimen.cpb_stroke_width);
        initAttributes(context, attributeSet);
        this.mMaxProgress = 100;
        this.mState = State.IDLE;
        this.mStateManager = new StateManager(this);
        setText(this.mIdleText);
        initIdleStateDrawable();
        setBackgroundCompat(this.mIdleStateDrawable);
    }

    private void initErrorStateDrawable() {
        StrokeGradientDrawable drawablePressed = createDrawable(getPressedColor(this.mErrorColorState));
        this.mErrorStateDrawable = new StateListDrawable();
        this.mErrorStateDrawable.addState(new int[]{16842919}, drawablePressed.getGradientDrawable());
        this.mErrorStateDrawable.addState(StateSet.WILD_CARD, this.background.getGradientDrawable());
    }

    private void initCompleteStateDrawable() {
        StrokeGradientDrawable drawablePressed = createDrawable(getPressedColor(this.mCompleteColorState));
        this.mCompleteStateDrawable = new StateListDrawable();
        this.mCompleteStateDrawable.addState(new int[]{16842919}, drawablePressed.getGradientDrawable());
        this.mCompleteStateDrawable.addState(StateSet.WILD_CARD, this.background.getGradientDrawable());
    }

    private void initIdleStateDrawable() {
        int colorNormal = getNormalColor(this.mIdleColorState);
        int colorPressed = getPressedColor(this.mIdleColorState);
        int colorFocused = getFocusedColor(this.mIdleColorState);
        int colorDisabled = getDisabledColor(this.mIdleColorState);
        if (this.background == null) {
            this.background = createDrawable(colorNormal);
        }
        StrokeGradientDrawable drawableDisabled = createDrawable(colorDisabled);
        StrokeGradientDrawable drawableFocused = createDrawable(colorFocused);
        StrokeGradientDrawable drawablePressed = createDrawable(colorPressed);
        this.mIdleStateDrawable = new StateListDrawable();
        this.mIdleStateDrawable.addState(new int[]{16842919}, drawablePressed.getGradientDrawable());
        this.mIdleStateDrawable.addState(new int[]{16842908}, drawableFocused.getGradientDrawable());
        this.mIdleStateDrawable.addState(new int[]{-16842910}, drawableDisabled.getGradientDrawable());
        this.mIdleStateDrawable.addState(StateSet.WILD_CARD, this.background.getGradientDrawable());
    }

    private int getNormalColor(ColorStateList colorStateList) {
        return colorStateList.getColorForState(new int[]{16842910}, 0);
    }

    private int getPressedColor(ColorStateList colorStateList) {
        return colorStateList.getColorForState(new int[]{16842919}, 0);
    }

    private int getFocusedColor(ColorStateList colorStateList) {
        return colorStateList.getColorForState(new int[]{16842908}, 0);
    }

    private int getDisabledColor(ColorStateList colorStateList) {
        return colorStateList.getColorForState(new int[]{-16842910}, 0);
    }

    private StrokeGradientDrawable createDrawable(int color) {
        GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.cpb_background).mutate();
        drawable.setColor(color);
        drawable.setCornerRadius(this.mCornerRadius);
        StrokeGradientDrawable strokeGradientDrawable = new StrokeGradientDrawable(drawable);
        strokeGradientDrawable.setStrokeColor(color);
        strokeGradientDrawable.setStrokeWidth(this.mStrokeWidth);
        return strokeGradientDrawable;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        if (this.mState == State.COMPLETE) {
            initCompleteStateDrawable();
            setBackgroundCompat(this.mCompleteStateDrawable);
        } else if (this.mState == State.IDLE) {
            initIdleStateDrawable();
            setBackgroundCompat(this.mIdleStateDrawable);
        } else if (this.mState == State.ERROR) {
            initErrorStateDrawable();
            setBackgroundCompat(this.mErrorStateDrawable);
        }
        if (this.mState != State.PROGRESS) {
            super.drawableStateChanged();
        }
    }

    private void initAttributes(Context context, AttributeSet attributeSet) {
        TypedArray attr = getTypedArray(context, attributeSet, R.styleable.CircularProgressButton);
        if (attr != null) {
            try {
                this.mIdleText = attr.getString(4);
                this.mCompleteText = attr.getString(3);
                this.mErrorText = attr.getString(5);
                this.mProgressText = attr.getString(6);
                this.mIconComplete = attr.getResourceId(11, 0);
                this.mIconError = attr.getResourceId(10, 0);
                this.mCornerRadius = attr.getDimension(12, 0.0f);
                this.mPaddingProgress = attr.getDimensionPixelSize(13, 0);
                int blue = getColor(R.color.cpb_blue);
                int white = getColor(R.color.cpb_white);
                int grey = getColor(R.color.cpb_grey);
                this.mIdleColorState = getResources().getColorStateList(attr.getResourceId(0, R.color.cpb_idle_state_selector));
                this.mCompleteColorState = getResources().getColorStateList(attr.getResourceId(1, R.color.cpb_complete_state_selector));
                this.mErrorColorState = getResources().getColorStateList(attr.getResourceId(2, R.color.cpb_error_state_selector));
                this.mColorProgress = attr.getColor(7, white);
                this.mColorIndicator = attr.getColor(8, blue);
                this.mColorIndicatorBackground = attr.getColor(9, grey);
            } finally {
                attr.recycle();
            }
        }
    }

    /* access modifiers changed from: protected */
    public int getColor(int id) {
        return getResources().getColor(id);
    }

    /* access modifiers changed from: protected */
    public TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mProgress > 0 && this.mState == State.PROGRESS && !this.mMorphingInProgress) {
            if (this.mIndeterminateProgressMode) {
                drawIndeterminateProgress(canvas);
            } else {
                drawProgress(canvas);
            }
        }
    }

    private void drawIndeterminateProgress(Canvas canvas) {
        if (this.mAnimatedDrawable == null) {
            int offset = (getWidth() - getHeight()) / 2;
            this.mAnimatedDrawable = new CircularAnimatedDrawable(this.mColorIndicator, (float) this.mStrokeWidth);
            int left = offset + this.mPaddingProgress;
            int right = (getWidth() - offset) - this.mPaddingProgress;
            int bottom = getHeight() - this.mPaddingProgress;
            this.mAnimatedDrawable.setBounds(left, this.mPaddingProgress, right, bottom);
            this.mAnimatedDrawable.setCallback(this);
            this.mAnimatedDrawable.start();
            return;
        }
        this.mAnimatedDrawable.draw(canvas);
    }

    private void drawProgress(Canvas canvas) {
        if (this.mProgressDrawable == null) {
            int offset = (getWidth() - getHeight()) / 2;
            this.mProgressDrawable = new CircularProgressDrawable(getHeight() - (this.mPaddingProgress * 2), this.mStrokeWidth, this.mColorIndicator);
            int left = offset + this.mPaddingProgress;
            this.mProgressDrawable.setBounds(left, this.mPaddingProgress, left, this.mPaddingProgress);
        }
        this.mProgressDrawable.setSweepAngle((360.0f / ((float) this.mMaxProgress)) * ((float) this.mProgress));
        this.mProgressDrawable.draw(canvas);
    }

    public boolean isIndeterminateProgressMode() {
        return this.mIndeterminateProgressMode;
    }

    public void setIndeterminateProgressMode(boolean indeterminateProgressMode) {
        this.mIndeterminateProgressMode = indeterminateProgressMode;
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable who) {
        return who == this.mAnimatedDrawable || super.verifyDrawable(who);
    }

    private MorphingAnimation createMorphing() {
        this.mMorphingInProgress = true;
        MorphingAnimation animation = new MorphingAnimation(this, this.background);
        animation.setFromCornerRadius(this.mCornerRadius);
        animation.setToCornerRadius(this.mCornerRadius);
        animation.setFromWidth(getWidth());
        animation.setToWidth(getWidth());
        if (this.mConfigurationChanged) {
            animation.setDuration(1);
        } else {
            animation.setDuration(MorphingAnimation.DURATION_NORMAL);
        }
        this.mConfigurationChanged = false;
        return animation;
    }

    private MorphingAnimation createProgressMorphing(float fromCorner, float toCorner, int fromWidth, int toWidth) {
        this.mMorphingInProgress = true;
        MorphingAnimation animation = new MorphingAnimation(this, this.background);
        animation.setFromCornerRadius(fromCorner);
        animation.setToCornerRadius(toCorner);
        animation.setPadding((float) this.mPaddingProgress);
        animation.setFromWidth(fromWidth);
        animation.setToWidth(toWidth);
        if (this.mConfigurationChanged) {
            animation.setDuration(1);
        } else {
            animation.setDuration(MorphingAnimation.DURATION_NORMAL);
        }
        this.mConfigurationChanged = false;
        return animation;
    }

    private void morphToProgress() {
        setWidth(getWidth());
        setText(this.mProgressText);
        MorphingAnimation animation = createProgressMorphing(this.mCornerRadius, (float) getHeight(), getWidth(), getHeight());
        animation.setFromColor(getNormalColor(this.mIdleColorState));
        animation.setToColor(this.mColorProgress);
        animation.setFromStrokeColor(getNormalColor(this.mIdleColorState));
        animation.setToStrokeColor(this.mColorIndicatorBackground);
        animation.setListener(this.mProgressStateListener);
        animation.start();
    }

    private void morphProgressToComplete() {
        MorphingAnimation animation = createProgressMorphing((float) getHeight(), this.mCornerRadius, getHeight(), getWidth());
        animation.setFromColor(this.mColorProgress);
        animation.setToColor(getNormalColor(this.mCompleteColorState));
        animation.setFromStrokeColor(this.mColorIndicator);
        animation.setToStrokeColor(getNormalColor(this.mCompleteColorState));
        animation.setListener(this.mCompleteStateListener);
        animation.start();
    }

    private void morphIdleToComplete() {
        MorphingAnimation animation = createMorphing();
        animation.setFromColor(getNormalColor(this.mIdleColorState));
        animation.setToColor(getNormalColor(this.mCompleteColorState));
        animation.setFromStrokeColor(getNormalColor(this.mIdleColorState));
        animation.setToStrokeColor(getNormalColor(this.mCompleteColorState));
        animation.setListener(this.mCompleteStateListener);
        animation.start();
    }

    private void morphCompleteToIdle() {
        MorphingAnimation animation = createMorphing();
        animation.setFromColor(getNormalColor(this.mCompleteColorState));
        animation.setToColor(getNormalColor(this.mIdleColorState));
        animation.setFromStrokeColor(getNormalColor(this.mCompleteColorState));
        animation.setToStrokeColor(getNormalColor(this.mIdleColorState));
        animation.setListener(this.mIdleStateListener);
        animation.start();
    }

    private void morphErrorToIdle() {
        MorphingAnimation animation = createMorphing();
        animation.setFromColor(getNormalColor(this.mErrorColorState));
        animation.setToColor(getNormalColor(this.mIdleColorState));
        animation.setFromStrokeColor(getNormalColor(this.mErrorColorState));
        animation.setToStrokeColor(getNormalColor(this.mIdleColorState));
        animation.setListener(this.mIdleStateListener);
        animation.start();
    }

    private void morphIdleToError() {
        MorphingAnimation animation = createMorphing();
        animation.setFromColor(getNormalColor(this.mIdleColorState));
        animation.setToColor(getNormalColor(this.mErrorColorState));
        animation.setFromStrokeColor(getNormalColor(this.mIdleColorState));
        animation.setToStrokeColor(getNormalColor(this.mErrorColorState));
        animation.setListener(this.mErrorStateListener);
        animation.start();
    }

    private void morphProgressToError() {
        MorphingAnimation animation = createProgressMorphing((float) getHeight(), this.mCornerRadius, getHeight(), getWidth());
        animation.setFromColor(this.mColorProgress);
        animation.setToColor(getNormalColor(this.mErrorColorState));
        animation.setFromStrokeColor(this.mColorIndicator);
        animation.setToStrokeColor(getNormalColor(this.mErrorColorState));
        animation.setListener(this.mErrorStateListener);
        animation.start();
    }

    private void morphProgressToIdle() {
        MorphingAnimation animation = createProgressMorphing((float) getHeight(), this.mCornerRadius, getHeight(), getWidth());
        animation.setFromColor(this.mColorProgress);
        animation.setToColor(getNormalColor(this.mIdleColorState));
        animation.setFromStrokeColor(this.mColorIndicator);
        animation.setToStrokeColor(getNormalColor(this.mIdleColorState));
        animation.setListener(new OnAnimationEndListener() {
            public void onAnimationEnd() {
                CircularProgressButton.this.removeIcon();
                CircularProgressButton.this.setText(CircularProgressButton.this.mIdleText);
                boolean unused = CircularProgressButton.this.mMorphingInProgress = false;
                State unused2 = CircularProgressButton.this.mState = State.IDLE;
                CircularProgressButton.this.mStateManager.checkState(CircularProgressButton.this);
            }
        });
        animation.start();
    }

    /* access modifiers changed from: private */
    public void setIcon(int icon) {
        Drawable drawable = getResources().getDrawable(icon);
        if (drawable != null) {
            setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
            setPadding((getWidth() / 2) - (drawable.getIntrinsicWidth() / 2), 0, 0, 0);
        }
    }

    /* access modifiers changed from: protected */
    public void removeIcon() {
        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        setPadding(0, 0, 0, 0);
    }

    @SuppressLint({"NewApi"})
    public void setBackgroundCompat(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 16) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
        if (!this.mMorphingInProgress && getWidth() != 0) {
            this.mStateManager.saveProgress(this);
            if (this.mProgress >= this.mMaxProgress) {
                if (this.mState == State.PROGRESS) {
                    morphProgressToComplete();
                } else if (this.mState == State.IDLE) {
                    morphIdleToComplete();
                }
            } else if (this.mProgress > 0) {
                if (this.mState == State.IDLE) {
                    morphToProgress();
                } else if (this.mState == State.PROGRESS) {
                    invalidate();
                }
            } else if (this.mProgress == -1) {
                if (this.mState == State.PROGRESS) {
                    morphProgressToError();
                } else if (this.mState == State.IDLE) {
                    morphIdleToError();
                }
            } else if (this.mProgress != 0) {
            } else {
                if (this.mState == State.COMPLETE) {
                    morphCompleteToIdle();
                } else if (this.mState == State.PROGRESS) {
                    morphProgressToIdle();
                } else if (this.mState == State.ERROR) {
                    morphErrorToIdle();
                }
            }
        }
    }

    public int getProgress() {
        return this.mProgress;
    }

    public void setBackgroundColor(int color) {
        this.background.getGradientDrawable().setColor(color);
    }

    public void setStrokeColor(int color) {
        this.background.setStrokeColor(color);
    }

    public String getIdleText() {
        return this.mIdleText;
    }

    public String getCompleteText() {
        return this.mCompleteText;
    }

    public String getErrorText() {
        return this.mErrorText;
    }

    public void setIdleText(String text) {
        this.mIdleText = text;
    }

    public void setCompleteText(String text) {
        this.mCompleteText = text;
    }

    public void setErrorText(String text) {
        this.mErrorText = text;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            setProgress(this.mProgress);
        }
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        int unused = savedState.mProgress = this.mProgress;
        boolean unused2 = savedState.mIndeterminateProgressMode = this.mIndeterminateProgressMode;
        boolean unused3 = savedState.mConfigurationChanged = true;
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof SavedState) {
            SavedState savedState = (SavedState) state;
            this.mProgress = savedState.mProgress;
            this.mIndeterminateProgressMode = savedState.mIndeterminateProgressMode;
            this.mConfigurationChanged = savedState.mConfigurationChanged;
            super.onRestoreInstanceState(savedState.getSuperState());
            setProgress(this.mProgress);
            return;
        }
        super.onRestoreInstanceState(state);
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        /* access modifiers changed from: private */
        public boolean mConfigurationChanged;
        /* access modifiers changed from: private */
        public boolean mIndeterminateProgressMode;
        /* access modifiers changed from: private */
        public int mProgress;

        public SavedState(Parcelable parcel) {
            super(parcel);
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        private SavedState(Parcel in) {
            super(in);
            boolean z;
            boolean z2 = true;
            this.mProgress = in.readInt();
            if (in.readInt() == 1) {
                z = true;
            } else {
                z = false;
            }
            this.mIndeterminateProgressMode = z;
            this.mConfigurationChanged = in.readInt() != 1 ? false : z2;
        }

        public void writeToParcel(Parcel out, int flags) {
            int i;
            int i2 = 1;
            super.writeToParcel(out, flags);
            out.writeInt(this.mProgress);
            if (this.mIndeterminateProgressMode) {
                i = 1;
            } else {
                i = 0;
            }
            out.writeInt(i);
            if (!this.mConfigurationChanged) {
                i2 = 0;
            }
            out.writeInt(i2);
        }
    }
}
