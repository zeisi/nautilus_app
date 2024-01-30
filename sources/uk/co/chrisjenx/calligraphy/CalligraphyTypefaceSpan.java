package uk.co.chrisjenx.calligraphy;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

public class CalligraphyTypefaceSpan extends MetricAffectingSpan {
    private final Typeface typeface;

    public CalligraphyTypefaceSpan(Typeface typeface2) {
        if (typeface2 == null) {
            throw new IllegalArgumentException("typeface is null");
        }
        this.typeface = typeface2;
    }

    public void updateDrawState(TextPaint drawState) {
        apply(drawState);
    }

    public void updateMeasureState(TextPaint paint) {
        apply(paint);
    }

    private void apply(Paint paint) {
        Typeface oldTypeface = paint.getTypeface();
        int fakeStyle = (oldTypeface != null ? oldTypeface.getStyle() : 0) & (this.typeface.getStyle() ^ -1);
        if ((fakeStyle & 1) != 0) {
            paint.setFakeBoldText(true);
        }
        if ((fakeStyle & 2) != 0) {
            paint.setTextSkewX(-0.25f);
        }
        paint.setTypeface(this.typeface);
    }
}
