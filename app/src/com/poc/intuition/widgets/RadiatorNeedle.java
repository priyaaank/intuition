package com.poc.intuition.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class RadiatorNeedle extends View {

    private Context context;
    private int needleWidthAtMiddle;
    private int needlePadding;
    private int centerXCord;
    private int centerYCord;
    private int containerHeight;
    private int containerWidth;

    public RadiatorNeedle(Context context) {
        super(context);
        this.context = context;
    }

    public RadiatorNeedle(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public RadiatorNeedle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setupDimensions();
        drawNeedle(canvas);
        super.onDraw(canvas);

    }

    private void drawNeedle(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setShadowLayer(10, 0, 0, Color.RED);

        Path path = new Path();
        path.moveTo(centerXCord+needleWidthAtMiddle, centerYCord);
        path.lineTo(centerXCord, containerHeight-needlePadding);
        path.lineTo(centerXCord-needleWidthAtMiddle, centerYCord);
        path.lineTo(centerXCord, 0+needlePadding);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void setupDimensions() {
        this.containerHeight = getHeight();
        this.containerWidth = getWidth();
        this.centerXCord = containerWidth/2;
        this.centerYCord = containerHeight/2;
        this.needleWidthAtMiddle = unitInDPs(50);
        this.needlePadding = unitInDPs(15);
    }

    private int unitInDPs(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }
}
