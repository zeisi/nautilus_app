package com.verticalbargraphiclibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import com.nautilus.omni.bleservices.ble.omnitrainer.OmniDictionaryKeys;
import java.util.ArrayList;
import java.util.Iterator;

public class VerticalBarGraphicView extends View {
    private static final int DEFAULT_BACKGROUND_COLOR = Color.parseColor("#f2f3f2");
    private static final int DEFAULT_BACKGROUND_LINES_BOTTOM_MARGIN = 6;
    private static final int DEFAULT_BOTTOM_LINE_COLOR = Color.parseColor("#6d6d6d");
    private static final int DEFAULT_BOTTOM_TEXT_SIZE = 13;
    private static final int DEFAULT_FOREGROUND_COLOR = Color.parseColor("#c41230");
    private static final int DEFAULT_GRAPHIC_BAR_BOTTOM_MARGIN = 8;
    private static final int DEFAULT_GRAPHIC_BAR_SIDE_MARGIN = 5;
    private static final int DEFAULT_GRAPHIC_BAR_WIDTH = 38;
    private static final int DEFAULT_GRAPHIC_LEFT_MARGIN = 35;
    private static final int DEFAULT_GRAPHIC_TOP_MARGIN = 35;
    private static final int DEFAULT_LEFT_SIDE_TEXT_BOTTOM_MARGIN = 10;
    private static final int DEFAULT_LEFT_TEXT_SIZE = 13;
    private static final int DEFAULT_TEXT_COLOR = Color.parseColor("#ffffff");
    private final int BAR_DEFAULT_AMOUNT;
    private Runnable animator;
    private boolean mAutoSetWidth;
    private int mBackgroundLinesBottomMargin;
    private Paint mBackgroundLinesPainter;
    private Paint mBackgroundPaint;
    private Paint mBottomLinePainter;
    private int mBottomTextDescent;
    private int mBottomTextHeight;
    private ArrayList<String> mBottomTextList;
    private Paint mBottomTextPainter;
    private boolean mCalculateAutomaticBarsWidth;
    private Context mContext;
    private boolean mDrawBottomLine;
    private boolean mDrawBottomValues;
    private boolean mDrawLeftSideValues;
    private boolean mDrawTopValues;
    private int mFirstColumnColor;
    private int mGraphicBarWidth;
    private int mGraphicBarsBottomMargin;
    private Paint mGraphicBarsPainter;
    private Typeface mGraphicBarsTopValuesTypeface;
    private Typeface mGraphicBottomTextTypeface;
    private int mGraphicComponentLeftMargin;
    private Typeface mGraphicLeftSideTextTypeface;
    private int mGraphicTopMargin;
    private boolean mIsMaxValueZero;
    private Boolean mIsTime;
    private int mLeftSideTextBottomMargin;
    private Paint mLeftSideTextPainter;
    private double mMaximumGraphicValue;
    private int mOthersColumnsColor;
    /* access modifiers changed from: private */
    public ArrayList<Double> mPercentList;
    private Rect mRect;
    private int mSpaceBetweenGraphicBars;
    /* access modifiers changed from: private */
    public ArrayList<Double> mTargetPercentList;
    private ArrayList<String> mTopTextList;
    private Paint mTopValuesTextPainter;
    private boolean printDifferentColorFirstColumn;

    public VerticalBarGraphicView(Context context) {
        this(context, (AttributeSet) null);
    }

    public VerticalBarGraphicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.BAR_DEFAULT_AMOUNT = 30;
        this.mTopTextList = new ArrayList<>();
        this.mBottomTextList = new ArrayList<>();
        this.mMaximumGraphicValue = 0.0d;
        this.mAutoSetWidth = true;
        this.mIsTime = false;
        this.animator = new Runnable() {
            public void run() {
                boolean needNewFrame = false;
                for (int counter = 0; counter < VerticalBarGraphicView.this.mTargetPercentList.size(); counter++) {
                    if (((Double) VerticalBarGraphicView.this.mPercentList.get(counter)).doubleValue() < ((Double) VerticalBarGraphicView.this.mTargetPercentList.get(counter)).doubleValue()) {
                        VerticalBarGraphicView.this.mPercentList.set(counter, Double.valueOf(((Double) VerticalBarGraphicView.this.mPercentList.get(counter)).doubleValue() + 0.019999999552965164d));
                        needNewFrame = true;
                    } else if (((Double) VerticalBarGraphicView.this.mPercentList.get(counter)).doubleValue() > ((Double) VerticalBarGraphicView.this.mTargetPercentList.get(counter)).doubleValue()) {
                        VerticalBarGraphicView.this.mPercentList.set(counter, Double.valueOf(((Double) VerticalBarGraphicView.this.mPercentList.get(counter)).doubleValue() - 0.019999999552965164d));
                        needNewFrame = true;
                    }
                    if (Math.abs(((Double) VerticalBarGraphicView.this.mTargetPercentList.get(counter)).doubleValue() - ((Double) VerticalBarGraphicView.this.mPercentList.get(counter)).doubleValue()) < 0.019999999552965164d) {
                        VerticalBarGraphicView.this.mPercentList.set(counter, VerticalBarGraphicView.this.mTargetPercentList.get(counter));
                    }
                }
                if (needNewFrame) {
                    VerticalBarGraphicView.this.postDelayed(this, 20);
                }
                VerticalBarGraphicView.this.invalidate();
            }
        };
        this.mContext = context;
        initMiscellaneousValues();
        initMeasureDefaultValues();
        initTextPainters();
    }

    private void initMiscellaneousValues() {
        initGraphicsBarPainter();
        this.mPercentList = new ArrayList<>();
        this.mDrawTopValues = false;
        this.mDrawBottomValues = false;
        this.mDrawLeftSideValues = false;
        this.mRect = new Rect();
    }

    private void initMeasureDefaultValues() {
        this.mGraphicComponentLeftMargin = GraphicUtils.dip2px(this.mContext, 35.0f);
        this.mGraphicTopMargin = GraphicUtils.dip2px(this.mContext, 35.0f);
        this.mGraphicBarWidth = GraphicUtils.dip2px(this.mContext, 38.0f);
        this.mSpaceBetweenGraphicBars = GraphicUtils.dip2px(this.mContext, 5.0f);
        this.mGraphicBarsBottomMargin = GraphicUtils.dip2px(this.mContext, 8.0f);
        this.mLeftSideTextBottomMargin = GraphicUtils.dip2px(this.mContext, 10.0f);
        this.mBackgroundLinesBottomMargin = GraphicUtils.dip2px(this.mContext, 6.0f);
    }

    private void initGraphicsBarPainter() {
        this.mBackgroundPaint = new Paint();
        this.mBackgroundPaint.setAntiAlias(true);
        this.mBackgroundPaint.setColor(DEFAULT_BACKGROUND_COLOR);
        this.mBottomLinePainter = new Paint(this.mBackgroundPaint);
        this.mBottomLinePainter.setColor(DEFAULT_BOTTOM_LINE_COLOR);
        this.mGraphicBarsPainter = new Paint(this.mBackgroundPaint);
        this.mGraphicBarsPainter.setColor(DEFAULT_FOREGROUND_COLOR);
    }

    private void initTextPainters() {
        initBottomTextPainter();
        initTopValuesTextPainter();
        initLeftSideTextPainter();
        initBackgroundLinesPainter();
    }

    private void initBottomTextPainter() {
        int textSize = GraphicUtils.sp2px(this.mContext, 13.0f);
        this.mBottomTextPainter = new Paint();
        this.mBottomTextPainter.setAntiAlias(true);
        this.mBottomTextPainter.setTextSize((float) textSize);
        this.mBottomTextPainter.setTextAlign(Paint.Align.CENTER);
        this.mBottomTextPainter.setColor(DEFAULT_TEXT_COLOR);
    }

    private void initTopValuesTextPainter() {
        int textSize = GraphicUtils.sp2px(this.mContext, 13.0f);
        this.mTopValuesTextPainter = new Paint();
        this.mTopValuesTextPainter.setAntiAlias(true);
        this.mTopValuesTextPainter.setTextSize((float) textSize);
        this.mTopValuesTextPainter.setTextAlign(Paint.Align.CENTER);
        this.mTopValuesTextPainter.setColor(DEFAULT_TEXT_COLOR);
    }

    private void initLeftSideTextPainter() {
        int textSize = GraphicUtils.sp2px(this.mContext, 13.0f);
        this.mLeftSideTextPainter = new Paint();
        this.mLeftSideTextPainter.setAntiAlias(true);
        this.mLeftSideTextPainter.setTextSize((float) textSize);
        this.mLeftSideTextPainter.setTextAlign(Paint.Align.CENTER);
        this.mLeftSideTextPainter.setColor(DEFAULT_TEXT_COLOR);
    }

    private void initBackgroundLinesPainter() {
        this.mBackgroundLinesPainter = new Paint();
        this.mBackgroundLinesPainter.setAntiAlias(true);
        this.mBackgroundLinesPainter.setTextAlign(Paint.Align.CENTER);
        this.mBackgroundLinesPainter.setColor(DEFAULT_TEXT_COLOR);
    }

    public void setGraphicBarWidth(float graphicBarWidth) {
        this.mGraphicBarWidth = GraphicUtils.dip2px(this.mContext, graphicBarWidth);
    }

    public void setGraphicBarSideMargin(float sideMargin) {
        this.mSpaceBetweenGraphicBars = GraphicUtils.dip2px(this.mContext, sideMargin);
    }

    public void setGraphicBarBottomMargin(float bottomMargin) {
        this.mGraphicBarsBottomMargin = GraphicUtils.dip2px(this.mContext, bottomMargin);
    }

    public void setLeftSideTextBottomMargin(float bottomMargin) {
        this.mLeftSideTextBottomMargin = GraphicUtils.dip2px(this.mContext, bottomMargin);
    }

    public void setBackgroundLinesBottomMargin(float bottomMargin) {
        this.mBackgroundLinesBottomMargin = GraphicUtils.dip2px(this.mContext, 6.0f);
    }

    public void setGraphicTopMargin(float topMargin) {
        this.mGraphicTopMargin = GraphicUtils.dip2px(this.mContext, topMargin);
    }

    public void setGraphicLeftMargin(float leftMargin) {
        this.mGraphicComponentLeftMargin = GraphicUtils.dip2px(this.mContext, leftMargin);
    }

    public void setGraphicBottomTextSize(float textSize) {
        this.mBottomTextPainter.setTextSize((float) GraphicUtils.sp2px(this.mContext, textSize));
    }

    public void setGraphicLeftSideTextSize(float textSize) {
        this.mLeftSideTextPainter.setTextSize((float) GraphicUtils.sp2px(this.mContext, textSize));
    }

    public void setDrawTopValues(boolean drawTopValues) {
        this.mDrawTopValues = drawTopValues;
    }

    public void setDrawBottomValues(boolean drawBottomValues) {
        this.mDrawBottomValues = drawBottomValues;
    }

    public void setDrawLeftSideValues(boolean drawLeftSideValues) {
        this.mDrawLeftSideValues = drawLeftSideValues;
    }

    public void setGraphicBarsColor(int color) {
        this.mGraphicBarsPainter.setColor(color);
    }

    public void setBottomTextColor(int color) {
        this.mBottomTextPainter.setColor(color);
    }

    public void setLeftSideTextColor(int color) {
        this.mLeftSideTextPainter.setColor(color);
    }

    public void setBackgroundLinesColor(int color) {
        this.mBackgroundLinesPainter.setColor(color);
    }

    public void setIsTimeData(Boolean isTime) {
        this.mIsTime = isTime;
    }

    public void setGraphicLeftSideTextTypeface(Typeface typeface) {
        this.mGraphicLeftSideTextTypeface = typeface;
        this.mLeftSideTextPainter.setTypeface(this.mGraphicLeftSideTextTypeface);
    }

    public void setGraphicBarsBottomTextTypeface(Typeface typeface) {
        this.mGraphicBottomTextTypeface = typeface;
        this.mBottomTextPainter.setTypeface(this.mGraphicBottomTextTypeface);
    }

    public void setGraphicBarsTopValuesTypeface(Typeface typeface) {
        this.mGraphicBarsTopValuesTypeface = typeface;
        this.mTopValuesTextPainter.setTypeface(this.mGraphicBarsTopValuesTypeface);
    }

    public void setTopTextList(ArrayList<String> topStringList) {
        this.mTopTextList = topStringList;
    }

    public void setBottomTextList(ArrayList<String> bottomStringList) {
        this.mBottomTextList = bottomStringList;
        Rect rect = new Rect();
        this.mBottomTextDescent = 0;
        Iterator<String> it = this.mBottomTextList.iterator();
        while (it.hasNext()) {
            String textValue = it.next();
            this.mBottomTextPainter.getTextBounds(textValue, 0, textValue.length(), rect);
            if (this.mBottomTextHeight < rect.height()) {
                this.mBottomTextHeight = rect.height();
            }
            if (this.mAutoSetWidth && this.mGraphicBarWidth < rect.width()) {
                this.mGraphicBarWidth = rect.width();
            }
            if (this.mBottomTextDescent < Math.abs(rect.bottom)) {
                this.mBottomTextDescent = Math.abs(rect.bottom);
            }
        }
        setMinimumWidth(2);
        postInvalidate();
    }

    public void setDataList(ArrayList<Double> valuesList, double maxListValue) {
        this.mTargetPercentList = new ArrayList<>();
        setMaximumGraphicValue(valuesList, maxListValue);
        if (this.mPercentList.isEmpty() || this.mPercentList.size() < this.mTargetPercentList.size()) {
            int temp = this.mTargetPercentList.size() - this.mPercentList.size();
            for (int i = 0; i < temp; i++) {
                this.mPercentList.add(Double.valueOf(1.0d));
            }
        } else if (this.mPercentList.size() > this.mTargetPercentList.size()) {
            int temp2 = this.mPercentList.size() - this.mTargetPercentList.size();
            for (int i2 = 0; i2 < temp2; i2++) {
                this.mPercentList.remove(this.mPercentList.size() - 1);
            }
        }
        setMinimumWidth(2);
        removeCallbacks(this.animator);
        post(this.animator);
    }

    private void setMaximumGraphicValue(ArrayList<Double> valuesList, double maxListValue) {
        if (maxListValue == 0.0d) {
            maxListValue = 1.0d;
            this.mIsMaxValueZero = true;
        } else {
            this.mIsMaxValueZero = false;
        }
        this.mMaximumGraphicValue = maxListValue;
        Iterator<Double> it = valuesList.iterator();
        while (it.hasNext()) {
            this.mTargetPercentList.add(Double.valueOf(1.0d - (checkIfElementPercentageIsTooLow(it.next()).doubleValue() / maxListValue)));
        }
    }

    private Double checkIfElementPercentageIsTooLow(Double element) {
        Double pointFivePercentOfMaxValue = Double.valueOf((this.mMaximumGraphicValue * 0.5d) / 100.0d);
        Double fourPercentMaxValue = Double.valueOf((this.mMaximumGraphicValue * 4.0d) / 100.0d);
        if (element.doubleValue() > pointFivePercentOfMaxValue.doubleValue() && element.doubleValue() <= fourPercentMaxValue.doubleValue()) {
            return fourPercentMaxValue;
        }
        if (element.doubleValue() < pointFivePercentOfMaxValue.doubleValue()) {
            return Double.valueOf(0.0d);
        }
        return element;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.mCalculateAutomaticBarsWidth) {
            loadBarsWidthDependingChartWidth(this.mPercentList.size());
        }
        drawBackgroundValuesLines(canvas);
        drawLeftSideValues(canvas);
        drawGraphicBar(canvas);
        drawBottomGraphicValues(canvas);
    }

    private void drawLeftSideValues(Canvas canvas) {
        if (this.mIsTime.booleanValue()) {
            drawTimeValues(canvas);
        } else {
            drawRegularNumericValues(canvas);
        }
    }

    private void drawTimeValues(Canvas canvas) {
        if (this.mDrawLeftSideValues) {
            canvas.drawText(Util.convertSecondsToDuration((long) this.mMaximumGraphicValue, false), ((float) this.mGraphicComponentLeftMargin) / 2.0f, ((float) this.mGraphicTopMargin) - ((float) this.mLeftSideTextBottomMargin), this.mLeftSideTextPainter);
            canvas.drawText(Util.convertSecondsToDuration(((long) this.mMaximumGraphicValue) / 2, false), ((float) this.mGraphicComponentLeftMargin) / 2.0f, (float) (getHeight() / 2), this.mLeftSideTextPainter);
            canvas.drawText(Util.convertSecondsToDuration(0, false), ((float) this.mGraphicComponentLeftMargin) / 2.0f, (float) (((getHeight() - this.mBottomTextHeight) - this.mBackgroundLinesBottomMargin) - this.mLeftSideTextBottomMargin), this.mLeftSideTextPainter);
        }
    }

    private void drawRegularNumericValues(Canvas canvas) {
        if (this.mDrawLeftSideValues) {
            canvas.drawText(getLeftSideValue(this.mMaximumGraphicValue), ((float) this.mGraphicComponentLeftMargin) / 2.0f, ((float) this.mGraphicTopMargin) - ((float) this.mLeftSideTextBottomMargin), this.mLeftSideTextPainter);
            canvas.drawText(getLeftSideValue(this.mMaximumGraphicValue / 2.0d), ((float) this.mGraphicComponentLeftMargin) / 2.0f, (float) (getHeight() / 2), this.mLeftSideTextPainter);
            canvas.drawText("0", ((float) this.mGraphicComponentLeftMargin) / 2.0f, (float) (((getHeight() - this.mBottomTextHeight) - this.mBackgroundLinesBottomMargin) - this.mLeftSideTextBottomMargin), this.mLeftSideTextPainter);
        }
    }

    private void drawBackgroundValuesLines(Canvas canvas) {
        if (this.mDrawLeftSideValues) {
            canvas.drawLine(0.0f, (float) ((getHeight() - this.mBottomTextHeight) - this.mBackgroundLinesBottomMargin), (float) getWidth(), (float) ((getHeight() - this.mBottomTextHeight) - this.mBackgroundLinesBottomMargin), this.mBackgroundLinesPainter);
            canvas.drawLine(0.0f, (float) ((((getHeight() - this.mBottomTextHeight) - this.mBackgroundLinesBottomMargin) + this.mGraphicTopMargin) / 2), (float) getWidth(), (float) ((((getHeight() - this.mBottomTextHeight) - this.mBackgroundLinesBottomMargin) + this.mGraphicTopMargin) / 2), this.mBackgroundLinesPainter);
            canvas.drawLine(0.0f, (float) this.mGraphicTopMargin, (float) getWidth(), (float) this.mGraphicTopMargin, this.mBackgroundLinesPainter);
        }
    }

    private String getLeftSideValue(double value) {
        if (this.mIsMaxValueZero) {
            value = 0.0d;
        }
        if (value > 99.0d) {
            return String.valueOf((int) value);
        }
        return Util.convertDoubleToOneDecimal(value);
    }

    private void drawGraphicBar(Canvas canvas) {
        int incrementalCounter = 1;
        int graphicValuesCounter = 0;
        if (this.mPercentList != null && !this.mPercentList.isEmpty()) {
            Iterator<Double> it = this.mPercentList.iterator();
            while (it.hasNext()) {
                Double next = it.next();
                double percent = this.mTargetPercentList.get(incrementalCounter - 1).doubleValue();
                if (this.mDrawTopValues) {
                    drawGraphicTopValues(canvas, incrementalCounter, graphicValuesCounter, percent);
                }
                if (this.mDrawBottomLine) {
                    drawBottomLine(canvas, incrementalCounter);
                }
                if (percent == 1.0d) {
                    drawBottomLines(canvas, incrementalCounter);
                } else {
                    drawVerticalBars(canvas, incrementalCounter);
                }
                graphicValuesCounter++;
                incrementalCounter++;
            }
        }
    }

    private void drawVerticalBars(Canvas canvas, int incrementalCounter) {
        if (this.printDifferentColorFirstColumn && incrementalCounter == 1) {
            this.mGraphicBarsPainter.setColor(this.mFirstColumnColor);
        } else if (this.printDifferentColorFirstColumn && incrementalCounter > 1) {
            this.mGraphicBarsPainter.setColor(this.mOthersColumnsColor);
        }
        this.mRect.set((this.mGraphicBarWidth * (incrementalCounter - 1)) + this.mGraphicComponentLeftMargin + (this.mSpaceBetweenGraphicBars * incrementalCounter), ((int) (((double) ((getHeight() - this.mGraphicTopMargin) - this.mBottomTextHeight)) * this.mPercentList.get(incrementalCounter - 1).doubleValue())) + this.mGraphicTopMargin, this.mGraphicComponentLeftMargin + ((this.mSpaceBetweenGraphicBars + this.mGraphicBarWidth) * incrementalCounter), (getHeight() - this.mBottomTextHeight) - this.mGraphicBarsBottomMargin);
        canvas.drawRect(this.mRect, this.mGraphicBarsPainter);
    }

    private void drawGraphicTopValues(Canvas canvas, int incrementalCounter, int graphicValuesCounter, double percent) {
        int defaultMargin = 30;
        if (percent == 1.0d) {
            defaultMargin = 60;
        }
        canvas.drawText(this.mTopTextList.get(graphicValuesCounter), (float) (this.mGraphicComponentLeftMargin + (this.mSpaceBetweenGraphicBars * incrementalCounter) + (this.mGraphicBarWidth * (incrementalCounter - 1)) + (this.mGraphicBarWidth / 2)), (float) ((((int) (((double) ((getHeight() - this.mGraphicTopMargin) - this.mBottomTextHeight)) * this.mPercentList.get(incrementalCounter - 1).doubleValue())) + this.mGraphicTopMargin) - defaultMargin), this.mTopValuesTextPainter);
    }

    private void drawBottomLines(Canvas canvas, int incrementalCounter) {
        if (this.printDifferentColorFirstColumn && incrementalCounter == 1) {
            this.mGraphicBarsPainter.setColor(this.mFirstColumnColor);
        } else if (this.printDifferentColorFirstColumn && incrementalCounter > 1) {
            this.mGraphicBarsPainter.setColor(this.mOthersColumnsColor);
        }
        this.mRect.set(this.mGraphicComponentLeftMargin + (this.mSpaceBetweenGraphicBars * incrementalCounter) + (this.mGraphicBarWidth * (incrementalCounter - 1)), this.mGraphicTopMargin + (((getHeight() - this.mGraphicTopMargin) - this.mBottomTextHeight) - (this.mGraphicBarsBottomMargin + 4)), this.mGraphicComponentLeftMargin + ((this.mSpaceBetweenGraphicBars + this.mGraphicBarWidth) * incrementalCounter), (getHeight() - this.mBottomTextHeight) - this.mGraphicBarsBottomMargin);
        canvas.drawRect(this.mRect, this.mGraphicBarsPainter);
    }

    private void drawBottomLine(Canvas canvas, int incrementalCounter) {
        int marginLeft = 0;
        if (incrementalCounter == 1) {
            marginLeft = this.mGraphicComponentLeftMargin + (this.mSpaceBetweenGraphicBars * incrementalCounter);
        }
        this.mRect.set((this.mGraphicBarWidth * (incrementalCounter - 1)) + marginLeft, this.mGraphicTopMargin + (((getHeight() - this.mGraphicTopMargin) - this.mBottomTextHeight) - (this.mGraphicBarsBottomMargin + 2)) + 3, this.mGraphicComponentLeftMargin + ((this.mSpaceBetweenGraphicBars + this.mGraphicBarWidth) * incrementalCounter), ((getHeight() - this.mBottomTextHeight) - this.mGraphicBarsBottomMargin) + GraphicUtils.dpToPx(this.mContext, 1) + 3);
        canvas.drawRect(this.mRect, this.mBottomLinePainter);
    }

    private void drawBottomGraphicValues(Canvas canvas) {
        if (this.mDrawBottomValues && this.mBottomTextList != null && !this.mBottomTextList.isEmpty()) {
            int incrementalCounter = 1;
            Iterator<String> it = this.mBottomTextList.iterator();
            while (it.hasNext()) {
                drawBottomValues(canvas, it.next(), incrementalCounter);
                incrementalCounter++;
            }
        }
    }

    private void drawBottomValues(Canvas canvas, String textValue, int incrementalCounter) {
        canvas.drawText(textValue, (float) (this.mGraphicComponentLeftMargin + (this.mSpaceBetweenGraphicBars * incrementalCounter) + (this.mGraphicBarWidth * (incrementalCounter - 1)) + (this.mGraphicBarWidth / 2)), (float) (getHeight() - this.mBottomTextDescent), this.mBottomTextPainter);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int preferred = 0;
        if (this.mTopTextList != null) {
            preferred = (this.mTopTextList.size() * (this.mGraphicBarWidth + this.mSpaceBetweenGraphicBars)) + this.mGraphicComponentLeftMargin + 1;
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

    public void setPrintDifferentColorFirstColumn(boolean printDifferentColorFirstColumn2) {
        this.printDifferentColorFirstColumn = printDifferentColorFirstColumn2;
    }

    public void setOthersColumnsColor(int mOthersColumnsColor2) {
        this.mOthersColumnsColor = mOthersColumnsColor2;
    }

    public void setFirstColumnColor(int mFirstColumnColor2) {
        this.mFirstColumnColor = mFirstColumnColor2;
    }

    public void drawBottomLine(boolean mDrawBottomLine2) {
        this.mDrawBottomLine = mDrawBottomLine2;
    }

    public void setCalculateAutomaticBarsWidth(boolean mCalculateAutomaticBarsWidth2) {
        this.mCalculateAutomaticBarsWidth = mCalculateAutomaticBarsWidth2;
    }

    public void loadBarsWidthDependingChartWidth(int barsNum) {
        if (barsNum > 0 && !this.mDrawLeftSideValues) {
            this.mGraphicBarWidth = (getWidth() / barsNum) - (this.mGraphicComponentLeftMargin + this.mSpaceBetweenGraphicBars);
        } else if (barsNum > 0 && this.mDrawLeftSideValues) {
            this.mGraphicBarWidth = (getWidth() / barsNum) - ((this.mGraphicComponentLeftMargin / barsNum) + this.mSpaceBetweenGraphicBars);
        }
    }
}
