package com.nautilus.omni.customviews.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.nautilus.omni.R;
import com.nautilus.omni.bleservices.ble.omnitrainer.OmniDictionaryKeys;
import java.util.ArrayList;
import java.util.Iterator;

public class HorizontalBarGraphicView extends View {
    private final int BACKGROUND_COLOR;
    private final int BAR_BOTTOM_MARGIN;
    private final int BAR_BOTTOM_MARK;
    private final int BAR_SIDE_MARGIN;
    private final int BLACK_COLOR;
    private final int FIRST_GRAPHIC_BAR;
    private final int FOREGROUND_COLOR;
    private final int MINI_BAR_WIDTH;
    private int TEXT_BOTTOM_MARGIN;
    private final int TEXT_COLOR;
    private final int TEXT_TOP_MARGIN;
    private int Y_AXIS_SPACE;
    private Runnable animator;
    private boolean autoSetWidth;
    private ArrayList<String> barSideTextList;
    private Paint bgPaint;
    private int bottomTextDescent;
    private int bottomTextHeight;
    Context context;
    private Paint fgPaint;
    /* access modifiers changed from: private */
    public boolean mAnimation;
    private float mBarWidth;
    private int mFirsGraphicBarColor;
    private int mGraphicBarsColor;
    private float mSideBarLabelTextSize;
    private float mSideBarLabelXAxisPosition;
    private boolean mSideValuesDrawn;
    private float mTextSideHeight;
    private float mTopLabelTextSize;
    private int mTopValuesTextColor;
    Typeface mTypefaceBold;
    Typeface mTypefaceItalic;
    Typeface mTypefaceRegular;
    double myMax;
    /* access modifiers changed from: private */
    public ArrayList<Double> percentList;
    private int realTopMargin;
    private Rect rect;
    Bitmap star;
    /* access modifiers changed from: private */
    public ArrayList<Double> targetPercentList;
    private Paint textPaintSideValues;
    private Paint textPaintTopValues;
    private int topMargin;
    private ArrayList<String> topTextList;

    public boolean isAnimation() {
        return this.mAnimation;
    }

    public void setAnimation(boolean mAnimation2) {
        this.mAnimation = mAnimation2;
    }

    public HorizontalBarGraphicView(Context context2) {
        this(context2, (AttributeSet) null);
    }

    public HorizontalBarGraphicView(Context context2, AttributeSet attrs) {
        super(context2, attrs);
        this.myMax = 0.0d;
        this.autoSetWidth = true;
        this.topTextList = new ArrayList<>();
        this.barSideTextList = new ArrayList<>();
        this.TEXT_COLOR = Color.parseColor("#3d3d3d");
        this.BACKGROUND_COLOR = Color.parseColor("#f2f3f2");
        this.BLACK_COLOR = Color.parseColor("#f2f2f2");
        this.FOREGROUND_COLOR = Color.parseColor("#c41230");
        this.FIRST_GRAPHIC_BAR = 0;
        this.mAnimation = false;
        this.animator = new Runnable() {
            public void run() {
                boolean needNewFrame = false;
                if (HorizontalBarGraphicView.this.mAnimation) {
                    for (int i = 0; i < HorizontalBarGraphicView.this.targetPercentList.size(); i++) {
                        if (((Double) HorizontalBarGraphicView.this.percentList.get(i)).doubleValue() < ((Double) HorizontalBarGraphicView.this.targetPercentList.get(i)).doubleValue()) {
                            HorizontalBarGraphicView.this.percentList.set(i, Double.valueOf(((Double) HorizontalBarGraphicView.this.percentList.get(i)).doubleValue() + 0.019999999552965164d));
                            needNewFrame = true;
                        } else if (((Double) HorizontalBarGraphicView.this.percentList.get(i)).doubleValue() > ((Double) HorizontalBarGraphicView.this.targetPercentList.get(i)).doubleValue()) {
                            HorizontalBarGraphicView.this.percentList.set(i, Double.valueOf(((Double) HorizontalBarGraphicView.this.percentList.get(i)).doubleValue() - 0.019999999552965164d));
                            needNewFrame = true;
                        }
                        if (Math.abs(((Double) HorizontalBarGraphicView.this.targetPercentList.get(i)).doubleValue() - ((Double) HorizontalBarGraphicView.this.percentList.get(i)).doubleValue()) < 0.019999999552965164d) {
                            HorizontalBarGraphicView.this.percentList.set(i, HorizontalBarGraphicView.this.targetPercentList.get(i));
                        }
                    }
                    if (needNewFrame) {
                        HorizontalBarGraphicView.this.postDelayed(this, 1);
                    }
                } else {
                    for (int i2 = 0; i2 < HorizontalBarGraphicView.this.targetPercentList.size(); i2++) {
                        HorizontalBarGraphicView.this.percentList.set(i2, HorizontalBarGraphicView.this.targetPercentList.get(i2));
                    }
                }
                HorizontalBarGraphicView.this.invalidate();
            }
        };
        this.context = context2;
        this.bgPaint = new Paint();
        this.bgPaint.setAntiAlias(true);
        this.bgPaint.setColor(this.BACKGROUND_COLOR);
        this.fgPaint = new Paint(this.bgPaint);
        this.fgPaint.setColor(this.FOREGROUND_COLOR);
        this.rect = new Rect();
        this.Y_AXIS_SPACE = 0;
        this.topMargin = 0;
        this.realTopMargin = 0;
        int textSize = GraphicUtils.sp2px(context2, 15.0f);
        this.mBarWidth = getResources().getDimension(R.dimen.horizontal_graphic_bar_width);
        this.mTextSideHeight = getResources().getDimension(R.dimen.last_week_horizontal_graphic_side_value_height);
        this.mSideBarLabelTextSize = getResources().getDimension(R.dimen.last_week_horizontal_graphic_side_value_text_size);
        this.mTopLabelTextSize = getResources().getDimension(R.dimen.last_week_horizontal_graphic_top_value_text_size);
        this.mSideBarLabelXAxisPosition = getResources().getDimension(R.dimen.last_week_horizontal_graphic_side_value_x_axis_position);
        this.mSideValuesDrawn = false;
        TypedValue outValue = new TypedValue();
        getResources().getValue(R.dimen.home_graphic_width, outValue, true);
        this.mTypefaceRegular = Typeface.createFromAsset(context2.getAssets(), "fonts/Lato-Regular.ttf");
        this.mTypefaceBold = Typeface.createFromAsset(context2.getAssets(), "fonts/Lato-Bold.ttf");
        this.mTypefaceItalic = Typeface.create(Typeface.DEFAULT, 2);
        this.MINI_BAR_WIDTH = GraphicUtils.dip2px(context2, outValue.getFloat());
        this.BAR_SIDE_MARGIN = 0;
        this.BAR_BOTTOM_MARK = GraphicUtils.dip2px(context2, 2.0f);
        this.BAR_BOTTOM_MARGIN = 0;
        this.TEXT_TOP_MARGIN = 0;
        this.TEXT_BOTTOM_MARGIN = 0;
        this.textPaintSideValues = new Paint();
        this.textPaintSideValues.setAntiAlias(true);
        this.textPaintSideValues.setColor(this.TEXT_COLOR);
        this.textPaintSideValues.setTextSize((float) textSize);
        this.textPaintSideValues.setTextAlign(Paint.Align.LEFT);
        this.textPaintTopValues = new Paint();
        this.textPaintTopValues.setAntiAlias(true);
        this.textPaintTopValues.setColor(this.TEXT_COLOR);
        this.textPaintTopValues.setTextAlign(Paint.Align.LEFT);
        this.percentList = new ArrayList<>();
    }

    public void setTopTextList(ArrayList<String> topStringList) {
        this.topTextList = topStringList;
        this.bottomTextDescent = 0;
        setMinimumWidth(2);
        postInvalidate();
    }

    public void setBarSideTextList(ArrayList<String> bottomStringList) {
        this.barSideTextList = bottomStringList;
        postInvalidate();
    }

    public void setDataList(ArrayList<Double> list, double max) {
        this.targetPercentList = new ArrayList<>();
        if (max == 0.0d) {
            max = 1.0d;
        }
        this.myMax = max;
        Iterator<Double> it = list.iterator();
        while (it.hasNext()) {
            this.targetPercentList.add(Double.valueOf(1.0d - (it.next().doubleValue() / max)));
        }
        if (this.percentList.isEmpty() || this.percentList.size() < this.targetPercentList.size()) {
            int temp = this.targetPercentList.size() - this.percentList.size();
            for (int i = 0; i < temp; i++) {
                this.percentList.add(Double.valueOf(1.0d));
            }
        } else if (this.percentList.size() > this.targetPercentList.size()) {
            int temp2 = this.percentList.size() - this.targetPercentList.size();
            for (int i2 = 0; i2 < temp2; i2++) {
                this.percentList.remove(this.percentList.size() - 1);
            }
        }
        setMinimumWidth(2);
        removeCallbacks(this.animator);
        post(this.animator);
    }

    public void setmGraphicBarsColor(int mGraphicBarsColor2) {
        this.mGraphicBarsColor = mGraphicBarsColor2;
    }

    public void setmFirsGraphicBarColor(int mFirsGraphicBarColor2) {
        this.mFirsGraphicBarColor = mFirsGraphicBarColor2;
    }

    public void setmTopValuesTextColor(int mTopValuesTextColor2) {
        this.mTopValuesTextColor = mTopValuesTextColor2;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        drawGraphicBar(canvas);
    }

    private void drawGraphicBar(Canvas canvas) {
        int incrementalCounter = 0;
        if (this.percentList != null && !this.percentList.isEmpty()) {
            Iterator<Double> it = this.percentList.iterator();
            while (it.hasNext()) {
                Double next = it.next();
                this.rect.set(0, (((int) this.mBarWidth) * incrementalCounter) + (this.BAR_SIDE_MARGIN * incrementalCounter), getWidth() - ((int) (((double) getWidth()) * this.percentList.get(incrementalCounter).doubleValue())), getHeight());
                if (incrementalCounter == 0) {
                    this.fgPaint.setColor(this.mFirsGraphicBarColor);
                } else {
                    this.fgPaint.setColor(this.mGraphicBarsColor);
                }
                canvas.drawRect(this.rect, this.fgPaint);
                drawGraphicBarSideValues(canvas, incrementalCounter);
                drawGraphicBarTopValues(canvas, incrementalCounter);
                incrementalCounter++;
            }
        }
    }

    private void drawGraphicBarSideValues(Canvas canvas, int incrementalCounter) {
        if (this.barSideTextList.size() > 0 && this.barSideTextList.size() == this.percentList.size()) {
            this.textPaintSideValues.setTypeface(this.mTypefaceRegular);
            this.textPaintSideValues.setTextSize(this.mSideBarLabelTextSize);
            if (incrementalCounter == 0) {
                this.textPaintSideValues.setColor(this.mFirsGraphicBarColor);
            } else {
                this.textPaintSideValues.setColor(this.mGraphicBarsColor);
            }
            canvas.drawText(this.barSideTextList.get(incrementalCounter), this.mSideBarLabelXAxisPosition, this.mTextSideHeight + this.mBarWidth + ((float) (this.BAR_SIDE_MARGIN * incrementalCounter)) + (this.mBarWidth * ((float) incrementalCounter)), this.textPaintSideValues);
        }
    }

    private void drawGraphicBarTopValues(Canvas canvas, int incrementalCounter) {
        if (this.topTextList.size() > 0 && this.topTextList.size() == this.percentList.size()) {
            this.textPaintTopValues.setTypeface(this.mTypefaceBold);
            this.textPaintTopValues.setTextSize(this.mTopLabelTextSize);
            this.textPaintTopValues.setColor(this.mTopValuesTextColor);
            if (this.targetPercentList.get(incrementalCounter).doubleValue() > 0.7d) {
                this.textPaintTopValues.setColor(this.BLACK_COLOR);
                canvas.drawText(this.topTextList.get(incrementalCounter), ((float) ((getWidth() - this.topMargin) - ((int) (((double) (getWidth() - this.topMargin)) * this.percentList.get(incrementalCounter).doubleValue())))) + getResources().getDimension(R.dimen.last_week_horizontal_graphic_top_numeric_value_factor), ((float) (this.BAR_SIDE_MARGIN * incrementalCounter)) + (this.mBarWidth * ((float) incrementalCounter)) + (this.mBarWidth / 1.4f), this.textPaintTopValues);
                return;
            }
            canvas.drawText(this.topTextList.get(incrementalCounter), (float) (((getWidth() - this.topMargin) - ((int) (((double) (getWidth() - this.topMargin)) * this.percentList.get(incrementalCounter).doubleValue()))) - getValueToShowLabelInsideGraphicBar(this.topTextList.get(incrementalCounter), this.textPaintTopValues)), ((float) (this.BAR_SIDE_MARGIN * incrementalCounter)) + (this.mBarWidth * ((float) incrementalCounter)) + (this.mBarWidth / 1.4f), this.textPaintTopValues);
        }
    }

    private int getValueToShowLabelInsideGraphicBar(String valueLabel, Paint paint) {
        int value = (int) getResources().getDimension(R.dimen.last_week_horizontal_graphic_top_numeric_value_factor);
        paint.setTextSize(20.0f * getContext().getResources().getDisplayMetrics().density);
        return value + ((int) paint.measureText(valueLabel));
    }

    private void drawBottomGraphicLines(Canvas canvas) {
        this.textPaintSideValues.setTypeface(this.mTypefaceItalic);
        if (this.percentList != null && !this.percentList.isEmpty()) {
            int incrementalCounter = 0;
            Iterator<Double> it = this.percentList.iterator();
            while (it.hasNext()) {
                Double next = it.next();
                if (incrementalCounter == 0) {
                    this.fgPaint.setColor(this.mFirsGraphicBarColor);
                } else {
                    this.fgPaint.setColor(this.mGraphicBarsColor);
                }
                this.rect.set(0, (this.BAR_SIDE_MARGIN * incrementalCounter) + (((int) this.mBarWidth) * incrementalCounter), 2, ((int) this.mBarWidth) + (this.BAR_SIDE_MARGIN * incrementalCounter) + (((int) this.mBarWidth) * incrementalCounter));
                canvas.drawRect(this.rect, this.fgPaint);
                incrementalCounter++;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int preferred = 0;
        if (this.topTextList != null) {
            preferred = (this.topTextList.size() * (((int) this.mBarWidth) + this.BAR_SIDE_MARGIN)) + this.Y_AXIS_SPACE + 1;
        }
        return getMeasurement(measureSpec, preferred);
    }

    private int measureHeight(int measureSpec) {
        return getMeasurement(measureSpec, OmniDictionaryKeys.UserGender);
    }

    private int getMeasurement(int measureSpec, int preferred) {
        int specSize = View.MeasureSpec.getSize(measureSpec);
        switch (View.MeasureSpec.getMode(measureSpec)) {
            case Integer.MIN_VALUE:
                return Math.min(preferred, specSize);
            case 1073741824:
                return specSize;
            default:
                return preferred;
        }
    }
}
