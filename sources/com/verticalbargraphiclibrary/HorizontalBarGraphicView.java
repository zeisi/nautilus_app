package com.verticalbargraphiclibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import java.util.ArrayList;
import java.util.Iterator;

public class HorizontalBarGraphicView extends View {
    private final int BAR_DEFAULT_AMOUNT;
    private final int BAR_SIDE_MARGIN;
    private final int DEFAULT_BACKGROUND_COLOR;
    private final int DEFAULT_FOREGROUND_COLOR;
    private final int DEFAULT_TEXT_COLOR;
    private Runnable animator;
    private Paint bgPaint;
    Context context;
    private Paint fgPaint;
    private boolean isTimeValues;
    private ArrayList<ChartColumnItem> mItems;
    private float mSideBarLabelXAxisPosition;
    private float mTextSideHeight;
    double myMax;
    /* access modifiers changed from: private */
    public ArrayList<Double> percentList;
    private Rect rect;
    /* access modifiers changed from: private */
    public ArrayList<Double> targetPercentList;
    private Paint textPaintSideValues;
    private Paint textPaintTopValues;
    private int topMargin;

    public HorizontalBarGraphicView(Context context2) {
        this(context2, (AttributeSet) null);
    }

    public HorizontalBarGraphicView(Context context2, AttributeSet attrs) {
        super(context2, attrs);
        this.BAR_DEFAULT_AMOUNT = 2;
        this.DEFAULT_TEXT_COLOR = Color.parseColor("#3d3d3d");
        this.DEFAULT_BACKGROUND_COLOR = Color.parseColor("#f2f3f2");
        this.DEFAULT_FOREGROUND_COLOR = Color.parseColor("#c41230");
        this.myMax = 0.0d;
        this.animator = new Runnable() {
            public void run() {
                boolean needNewFrame = false;
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
                HorizontalBarGraphicView.this.invalidate();
            }
        };
        this.context = context2;
        this.mItems = new ArrayList<>();
        this.bgPaint = new Paint();
        this.bgPaint.setAntiAlias(true);
        this.bgPaint.setColor(this.DEFAULT_BACKGROUND_COLOR);
        this.fgPaint = new Paint(this.bgPaint);
        this.fgPaint.setColor(this.DEFAULT_FOREGROUND_COLOR);
        this.rect = new Rect();
        this.topMargin = GraphicUtils.dip2px(context2, 0.0f);
        int textSize = GraphicUtils.sp2px(context2, 15.0f);
        this.mTextSideHeight = getResources().getDimension(R.dimen.last_week_horizontal_graphic_side_value_height);
        this.mSideBarLabelXAxisPosition = getResources().getDimension(R.dimen.last_week_horizontal_graphic_side_value_x_axis_position);
        getResources().getValue(R.dimen.home_graphic_width, new TypedValue(), true);
        this.BAR_SIDE_MARGIN = GraphicUtils.dip2px(context2, 13.0f);
        this.textPaintSideValues = new Paint();
        this.textPaintSideValues.setAntiAlias(true);
        this.textPaintSideValues.setColor(this.DEFAULT_TEXT_COLOR);
        this.textPaintSideValues.setTextSize((float) textSize);
        this.textPaintSideValues.setTextAlign(Paint.Align.LEFT);
        this.textPaintTopValues = new Paint();
        this.textPaintTopValues.setAntiAlias(true);
        this.textPaintTopValues.setColor(this.DEFAULT_TEXT_COLOR);
        this.textPaintTopValues.setTextAlign(Paint.Align.LEFT);
        this.percentList = new ArrayList<>();
    }

    public void setDataList(ArrayList<ChartColumnItem> list, double max) {
        this.mItems = list;
        this.targetPercentList = new ArrayList<>();
        if (max == 0.0d) {
            max = 1.0d;
        }
        this.myMax = max;
        Iterator<ChartColumnItem> it = list.iterator();
        while (it.hasNext()) {
            this.targetPercentList.add(Double.valueOf(1.0d - (it.next().getRealValue() / max)));
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

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        drawGraphicBar(canvas);
    }

    private void drawGraphicBar(Canvas canvas) {
        if (this.percentList != null && !this.percentList.isEmpty()) {
            int lastPosition = 0;
            for (int i = 0; i < this.percentList.size(); i++) {
                ChartColumnItem column = this.mItems.get(i);
                int newYPosition = lastPosition + ((int) column.getColumnWidth());
                drawPrincipalBar(canvas, i, lastPosition, newYPosition, column);
                drawGraphicBarSideValues(canvas, column, newYPosition);
                drawGraphicBarTopValues(canvas, i, lastPosition);
                lastPosition = (int) (((float) lastPosition) + ((float) (getBarMargin(column) + ((int) column.getColumnWidth()))) + this.mTextSideHeight);
            }
        }
    }

    private void drawPrincipalBar(Canvas canvas, int position, int lastPosition, int newYPosition, ChartColumnItem chartColumnItem) {
        this.rect.set(0, lastPosition, ((getWidth() - this.topMargin) - ((int) (((double) (getWidth() - this.topMargin)) * this.percentList.get(position).doubleValue()))) + 2, newYPosition);
        this.fgPaint.setColor(chartColumnItem.getColumnBackgroundColor());
        canvas.drawRect(this.rect, this.fgPaint);
    }

    private int getBarMargin(ChartColumnItem chartColumnItem) {
        return this.BAR_SIDE_MARGIN + ((int) chartColumnItem.getBottomTextSize());
    }

    private void drawGraphicBarSideValues(Canvas canvas, ChartColumnItem item, int lastPosition) {
        this.textPaintSideValues.setTypeface(item.getColumnBottomTextTypeface());
        this.textPaintSideValues.setTextSize(item.getBottomTextSize());
        this.textPaintSideValues.setColor(item.getBottomTextColor());
        canvas.drawText(item.getBottomText(), this.mSideBarLabelXAxisPosition, getBottomTextYPosition(item.getBottomTextSize(), lastPosition), this.textPaintSideValues);
    }

    private float getBottomTextYPosition(float textsize, int lastPosition) {
        return this.mTextSideHeight + textsize + ((float) lastPosition);
    }

    private void drawGraphicBarTopValues(Canvas canvas, int incrementalCounter, int lastPosition) {
        this.textPaintTopValues.setTypeface(this.mItems.get(incrementalCounter).getColumnCenterTextTypeface());
        this.textPaintTopValues.setTextSize(this.mItems.get(incrementalCounter).getCenterTextSize());
        this.textPaintTopValues.setColor(this.mItems.get(incrementalCounter).getCenterTextColor());
        if (this.targetPercentList.get(incrementalCounter).doubleValue() > 0.7d && !this.isTimeValues) {
            canvas.drawText(this.mItems.get(incrementalCounter).getCenterText(), getCenterTextOutXPosition(incrementalCounter), getCenterTextOutYPosition(incrementalCounter, lastPosition, this.textPaintTopValues), this.textPaintTopValues);
        } else if (this.targetPercentList.get(incrementalCounter).doubleValue() <= 0.6d || !this.isTimeValues) {
            canvas.drawText(this.mItems.get(incrementalCounter).getCenterText(), getCenterTextInXPosition(incrementalCounter), getCenterTextInYPosition(incrementalCounter, lastPosition, this.textPaintTopValues), this.textPaintTopValues);
        } else {
            canvas.drawText(this.mItems.get(incrementalCounter).getCenterText(), getCenterTextOutXPosition(incrementalCounter), getCenterTextOutYPosition(incrementalCounter, lastPosition, this.textPaintTopValues), this.textPaintTopValues);
        }
    }

    private float getCenterTextOutYPosition(int incrementalCounter, int lastPosition, Paint paint) {
        return ((this.mItems.get(incrementalCounter).getColumnWidth() / 2.0f) + ((float) lastPosition)) - ((paint.descent() + paint.ascent()) / 2.0f);
    }

    private float getCenterTextInYPosition(int incrementalCounter, int lastPosition, Paint paint) {
        return ((this.mItems.get(incrementalCounter).getColumnWidth() / 2.0f) + ((float) lastPosition)) - ((paint.descent() + paint.ascent()) / 2.0f);
    }

    private float getCenterTextOutXPosition(int incrementalCounter) {
        return ((float) ((getWidth() - this.topMargin) - ((int) (((double) (getWidth() - this.topMargin)) * this.percentList.get(incrementalCounter).doubleValue())))) + getResources().getDimension(R.dimen.last_week_horizontal_graphic_top_numeric_value_factor);
    }

    private float getCenterTextInXPosition(int incrementalCounter) {
        return (float) (((getWidth() - this.topMargin) - ((int) (((double) (getWidth() - this.topMargin)) * this.percentList.get(incrementalCounter).doubleValue()))) - getValueToShowLabelInsideGraphicBar(this.mItems.get(incrementalCounter).getCenterText()));
    }

    private int getValueToShowLabelInsideGraphicBar(String valueLabel) {
        float f;
        float dimension;
        int value = 0;
        for (char currentChar : valueLabel.toCharArray()) {
            if (Character.isDigit(currentChar)) {
                f = (float) value;
                dimension = getResources().getDimension(R.dimen.last_week_horizontal_graphic_top_numeric_value_factor);
            } else {
                f = (float) value;
                dimension = getResources().getDimension(R.dimen.last_week_horizontal_graphic_top_non_numeric_value_factor);
            }
            value = (int) (f + dimension);
        }
        return value;
    }

    private void drawBottomGraphicLines(Canvas canvas, int startPositionY, int endPositionY, ChartColumnItem chartColumnItem) {
        this.fgPaint.setColor(chartColumnItem.getColumnBackgroundColor());
        this.rect.set(0, startPositionY, 2, endPositionY);
        canvas.drawRect(this.rect, this.fgPaint);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        return getMeasurement(measureSpec, 100);
    }

    private int measureHeight(int measureSpec) {
        int preferred = 0;
        Iterator<ChartColumnItem> it = this.mItems.iterator();
        while (it.hasNext()) {
            ChartColumnItem column = it.next();
            preferred = (int) (((float) preferred) + ((float) (getBarMargin(column) + ((int) column.getColumnWidth()))) + this.mTextSideHeight);
        }
        return getMeasurement(measureSpec, preferred);
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

    public void isTimeValues(boolean timeValues) {
        this.isTimeValues = timeValues;
    }
}
