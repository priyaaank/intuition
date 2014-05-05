package com.poc.intuition.widgets;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class DonutExpenseSplit extends View {

    private Context context;
    private float containerPaddingXCord;
    private float containerPaddingYCord;
    private float topSemiCircleBoundaryXCord;
    private float topSemiCircleBoundaryYCord;
    private int containerHeight;
    private int containerWidth;
    private Double totalValue;
    private Double[] values;
    private static String[] colorArray = new String[] {
            "#78BD33",
            "#0DB631",
            "#BD7F31",
            "#235A2C",
            "#11BD45",
            "#21BB23",
            "#AABB23",
            "#BD7F31",
            "#235A2C",
            "#11BD45",
            "#21BB23",
            "#AABB23"
    };

    public DonutExpenseSplit(Context context, Double totalAmount, Double...values) {
        super(context);
        this.context = context;
        this.totalValue = totalAmount;
        this.values = values;
    }

    public DonutExpenseSplit(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public DonutExpenseSplit(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        prepareDimensions();
        drawArc(canvas);
        super.onDraw(canvas);
    }

    private void prepareDimensions() {
        this.containerHeight = getHeight();
        this.containerWidth = getWidth();
        this.containerPaddingXCord = unitInDPs(10);
        this.containerPaddingYCord = unitInDPs(10);
        this.topSemiCircleBoundaryXCord = this.containerWidth - containerPaddingXCord;
        this.topSemiCircleBoundaryYCord = this.containerHeight - containerPaddingYCord;

    }

    private void drawArc(Canvas canvas) {
        int index = 0;
        float sweep = 0;
        float lastSweep = 0;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(unitInDPs(20));
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#FED731"));
        RectF oval = new RectF(containerPaddingXCord, containerPaddingYCord, topSemiCircleBoundaryXCord, topSemiCircleBoundaryYCord);

        for(Double value  : values) {
            paint.setColor(Color.parseColor(colorArray[index++]));
            lastSweep = lastSweep + sweep;
            sweep = getAngleSweepForValue(value);
            canvas.drawArc(oval, lastSweep, sweep, true, paint);
        }

        paint.setColor(Color.parseColor("#dbd6d2"));
        canvas.drawCircle(getWidth()/2, getHeight()/2, unitInDPs(60), paint);
    }

    private float getAngleSweepForValue(Double value) {
        return (360*((float)(value/totalValue)));
    }

    private float unitInDPs(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }
}
