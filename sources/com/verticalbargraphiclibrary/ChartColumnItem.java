package com.verticalbargraphiclibrary;

import android.graphics.Typeface;

public class ChartColumnItem {
    private String mBottomText;
    private int mBottomTextColor;
    private float mBottomTextSize;
    private String mCenterText;
    private int mCenterTextColor;
    private float mCenterTextSize;
    private int mColumnBackgroundColor;
    private Typeface mColumnBottomTextTypeface;
    private Typeface mColumnCenterTextTypeface;
    private float mColumnWidth;
    private double mRealValue;

    public ChartColumnItem(int mColumnBackgroundColor2, int mBottomTextColor2, int mCenterTextColor2, float mCenterTextSize2, float mBottomTextSize2, Typeface mColumnCenterTextTypeface2, Typeface mColumnBottomTextTypeface2, String mBottomText2, String mCenterText2, double mRealValue2, float pColumnWidth) {
        this.mColumnBackgroundColor = mColumnBackgroundColor2;
        this.mBottomTextColor = mBottomTextColor2;
        this.mCenterTextColor = mCenterTextColor2;
        this.mCenterTextSize = mCenterTextSize2;
        this.mBottomTextSize = mBottomTextSize2;
        this.mColumnCenterTextTypeface = mColumnCenterTextTypeface2;
        this.mColumnBottomTextTypeface = mColumnBottomTextTypeface2;
        this.mBottomText = mBottomText2;
        this.mCenterText = mCenterText2;
        this.mRealValue = mRealValue2;
        this.mColumnWidth = pColumnWidth;
    }

    public float getColumnWidth() {
        return this.mColumnWidth;
    }

    public void setColumnWidth(float mColumnWidth2) {
        this.mColumnWidth = mColumnWidth2;
    }

    public float getCenterTextSize() {
        return this.mCenterTextSize;
    }

    public void setCenterTextSize(int mCenterTextSize2) {
        this.mCenterTextSize = (float) mCenterTextSize2;
    }

    public float getBottomTextSize() {
        return this.mBottomTextSize;
    }

    public void setBottomTextSize(int mBottomTextSize2) {
        this.mBottomTextSize = (float) mBottomTextSize2;
    }

    public int getColumnBackgroundColor() {
        return this.mColumnBackgroundColor;
    }

    public void setColumnBackgroundColor(int mColumnBackgroundColor2) {
        this.mColumnBackgroundColor = mColumnBackgroundColor2;
    }

    public int getBottomTextColor() {
        return this.mBottomTextColor;
    }

    public void setBottomTextColor(int mBottomTextColor2) {
        this.mBottomTextColor = mBottomTextColor2;
    }

    public int getCenterTextColor() {
        return this.mCenterTextColor;
    }

    public void setCenterTextColor(int mCenterTextColor2) {
        this.mCenterTextColor = mCenterTextColor2;
    }

    public Typeface getColumnCenterTextTypeface() {
        return this.mColumnCenterTextTypeface;
    }

    public void setColumnCenterTextTypeface(Typeface mColumnCenterTextTypeface2) {
        this.mColumnCenterTextTypeface = mColumnCenterTextTypeface2;
    }

    public Typeface getColumnBottomTextTypeface() {
        return this.mColumnBottomTextTypeface;
    }

    public void setColumnBottomTextTypeface(Typeface mColumnBottomTextTypeface2) {
        this.mColumnBottomTextTypeface = mColumnBottomTextTypeface2;
    }

    public String getBottomText() {
        return this.mBottomText;
    }

    public void setBottomText(String mBottomText2) {
        this.mBottomText = mBottomText2;
    }

    public String getCenterText() {
        return this.mCenterText;
    }

    public void setCenterText(String mCenterText2) {
        this.mCenterText = mCenterText2;
    }

    public double getRealValue() {
        return this.mRealValue;
    }

    public void setRealValue(double mRealValue2) {
        this.mRealValue = mRealValue2;
    }
}
