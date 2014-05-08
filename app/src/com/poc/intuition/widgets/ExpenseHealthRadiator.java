package com.poc.intuition.widgets;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class ExpenseHealthRadiator extends View {

    private Context context;
    private int containerHeight;
    private int containerWidth;
    private int centerXCord;
    private int centerYCord;
    private int containerPaddingYCord;
    private int containerPaddingXCord;
    private int topSemiCircleBoundaryXCord;
    private int topSemiCircleBoundaryYCord;
    private int topSemiCircleStartAngle;
    private int topSemiCircleEndAngle;
    private int bottomSemiCircleStartAngle;
    private int bottomSemiCircleEndAngle;
    private int concentricCircleRadius;
    private int dividerLineEndXCord;
    private int dividerLineEndYCord;
    private int dividerLineStartXCord;
    private int dividerLineStartYCord;
    private int needleWidthAtMiddle;
    private int needlePadding;

    public ExpenseHealthRadiator(Context context) {
        super(context);
        this.context = context;
    }

    public ExpenseHealthRadiator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public ExpenseHealthRadiator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        prepareDimensions();
        drawBottomSemicircleWithSegments(canvas);
        drawTopSemicircleWithSegments(canvas);
        drawConcentricCircle(canvas);
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

    private void drawCentralDivider(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#D4D5CD"));
        canvas.drawRect(dividerLineStartXCord, dividerLineStartYCord, dividerLineEndXCord, dividerLineEndYCord, paint);
    }

    private void drawTopSemicircleWithSegments(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        int colors[] = new int[] {
                Color.parseColor("#FAA619"),
                Color.parseColor("#F15923"),
                Color.parseColor("#ED1C23"),
                Color.parseColor("#BF1E2D")
        };
        int startAngle = 180;
        int angleSweep = 180/colors.length;
        RectF oval = new RectF(containerPaddingXCord, containerPaddingYCord, topSemiCircleBoundaryXCord, topSemiCircleBoundaryYCord);
        for(int colorIndex = 0; colorIndex < colors.length; colorIndex++) {
            paint.setColor(colors[colorIndex]);
            canvas.drawArc(oval, startAngle, angleSweep, true, paint);
            startAngle = startAngle + angleSweep;
        }
    }

    private void drawTopSemicircleWithGradient(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        int colors[] = new int[] {
                Color.parseColor("#FED731"),
                Color.parseColor("#FED731"),
                Color.parseColor("#F17724"),
                Color.parseColor("#EC441F"),
                Color.parseColor("#E7171E")
        };
        paint.setShader(new LinearGradient(0, 0, containerWidth, containerHeight, colors, null, Shader.TileMode.REPEAT));

        RectF oval = new RectF(containerPaddingXCord, containerPaddingYCord, topSemiCircleBoundaryXCord, topSemiCircleBoundaryYCord);
        canvas.drawArc(oval, topSemiCircleStartAngle, topSemiCircleEndAngle, true, paint);
    }

    private void drawBottomSemicircleWithSegments(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        int colors[] = new int[] {
                Color.parseColor("#056839"),
                Color.parseColor("#0C9443"),
                Color.parseColor("#3AB549"),
                Color.parseColor("#98CA3C")
        };
        int startAngle = 0;
        int angleSweep = 180/colors.length;
        RectF oval = new RectF(containerPaddingXCord, containerPaddingYCord, topSemiCircleBoundaryXCord, topSemiCircleBoundaryYCord);
        for(int colorIndex = 0; colorIndex < colors.length; colorIndex++) {
            paint.setColor(colors[colorIndex]);
            canvas.drawArc(oval, startAngle, angleSweep, true, paint);
            startAngle = startAngle + angleSweep;
        }
    }

    private void drawBottomSemicircleWithGradient(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        int colors[] = new int[] {
                Color.parseColor("#78BD33"),
                Color.parseColor("#77B631"),
                Color.parseColor("#3D7F31"),
                Color.parseColor("#0D5A2C")
        };
        paint.setShader(new LinearGradient(0, 0, containerWidth, containerHeight, colors, null, Shader.TileMode.REPEAT));

        RectF oval = new RectF(containerPaddingXCord, containerPaddingYCord, topSemiCircleBoundaryXCord, topSemiCircleBoundaryYCord);
        canvas.drawArc(oval, bottomSemiCircleStartAngle, bottomSemiCircleEndAngle, true, paint);
    }

    private void drawConcentricCircle(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(centerXCord, centerYCord, concentricCircleRadius, paint);
    }

    private void prepareDimensions() {
        this.containerHeight = getHeight();
        this.containerWidth = getWidth();
        this.topSemiCircleStartAngle = 0;
        this.topSemiCircleEndAngle = -180;
        this.bottomSemiCircleStartAngle = 0;
        this.bottomSemiCircleEndAngle = 180;
        this.centerXCord = containerWidth/2;
        this.centerYCord = containerHeight/2;
        this.containerPaddingXCord = unitInDPs(10);
        this.containerPaddingYCord = unitInDPs(10);
        this.concentricCircleRadius = (Math.min(this.containerHeight, this.containerWidth) - unitInDPs(58))/2;
        this.topSemiCircleBoundaryXCord = this.containerWidth - containerPaddingXCord;
        this.topSemiCircleBoundaryYCord = this.containerHeight - containerPaddingYCord;
        this.dividerLineStartYCord = centerYCord-unitInDPs(1);
        this.dividerLineEndYCord = centerYCord+unitInDPs(1);
        this.dividerLineStartXCord = containerPaddingXCord;
        this.dividerLineEndXCord = getWidth()-containerPaddingXCord;
        this.needleWidthAtMiddle = unitInDPs(50);
        this.needlePadding = unitInDPs(15);
    }

    private int unitInDPs(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }
}