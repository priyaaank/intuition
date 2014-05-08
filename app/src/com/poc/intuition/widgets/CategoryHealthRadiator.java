package com.poc.intuition.widgets;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.poc.intuition.R;

public class CategoryHealthRadiator {

    private RelativeLayout parentRadiatorHolder;
    private HealthArcs categoryHealthArcs;
    private OverlayCircle overlayCircle;
    private TextView percentage;
    private Context context;
    private Double totalAmount;
    private Double amountSpent;
    private int height;
    private int width;
    private RadiatorStyle overridenStyle;

    public CategoryHealthRadiator(Context context, Double totalAmount, Double amountSpent, int height, int width) {
        this.amountSpent = amountSpent;
        this.totalAmount = totalAmount;
        this.height = height;
        this.width = width;
        this.context = context;
        overridenStyle = new RadiatorStyle();
        overridenStyle.centerColor = "#72368C";
        overridenStyle.centerRadiusPercentageOfWidth = OverlayCircle.THIRTY_FIVE_PERCENT;
        overridenStyle.textColor = "#FFFFFF";
    }

    public CategoryHealthRadiator setStyleForOverspending() {
        overridenStyle.overrideRadialColorIfOverbudget = true;
        overridenStyle.centerRadiusPercentageOfWidth = .30f;
        overridenStyle.centerColor = "#FFFFFF";
        overridenStyle.textColor = "#864C9E";
        return this;
    }

    public CategoryHealthRadiator setStyleForHistoricGraph() {
        overridenStyle.centerRadiusPercentageOfWidth = .30f;
        overridenStyle.centerColor = "#FFFFFF";
        overridenStyle.textColor = "#864C9E";
        return this;
    }

    public RelativeLayout build() {
        categoryHealthArcs = new HealthArcs(context);
        overlayCircle = new OverlayCircle(context);
        percentage = createTextViewWithValue(percentageOfMoneySpent());
        initializeParentLayout();
        parentRadiatorHolder.addView(categoryHealthArcs);
        parentRadiatorHolder.addView(overlayCircle);
        parentRadiatorHolder.addView(percentage);
        animate();
        return parentRadiatorHolder;
    }

    private TextView createTextViewWithValue(int percentageOfMoneySpent) {
        TextView textView = new TextView(context);
        textView.setTextSize(20);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(height, width);
        textView.setLayoutParams(params);
        textView.setText(percentageOfMoneySpent+"%");
        textView.setGravity(Gravity.CENTER);
        textView.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        textView.setTextColor(Color.parseColor(overridenStyle.textColor));
        return textView;
    }

    private int percentageOfMoneySpent() {
        return (int)((amountSpent / totalAmount) *100);
    }

    private void animate() {
        Animation expandAnimation = AnimationUtils.loadAnimation(context, R.anim.expand);
        categoryHealthArcs.startAnimation(expandAnimation);
    }

    private void initializeParentLayout() {
        parentRadiatorHolder = new RelativeLayout(context);
        parentRadiatorHolder.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(height, width);
        parentRadiatorHolder.setLayoutParams(params);
    }

    private class HealthArcs extends View {

        public static final int START_ANGLE = 160;
        public static final float TEN_PERCENT = 0.1f;
        private float containerHeight;
        private float containerWidth;
        private float containerPaddingXCord;
        private float containerPaddingYCord;
        private float topSemiCircleBoundaryXCord;
        private float topSemiCircleBoundaryYCord;
        private int sweepAngleForSegment;
        private int totalSegmentCount;

        private String[] segmentColors = new String[]{
                "#FFCB06",
                "#FAA619",
                "#F58221",
                "#F15923",
                "#ED1C23",
                "#fD5A2F"
        };


        public HealthArcs(Context context) {
            super(context);
        }

        public HealthArcs(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public HealthArcs(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            prepareDimensions();
            drawSegmentsForValue(canvas);
            super.onDraw(canvas);
        }

        private void drawSegmentsForValue(Canvas canvas) {
            int lastSweep = START_ANGLE;
            RectF oval = new RectF(containerPaddingXCord, containerPaddingYCord, topSemiCircleBoundaryXCord, topSemiCircleBoundaryYCord);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            int segmentToDrawCount = getSegmentToDrawCount();
            for (int segmentCount = 0; segmentCount < totalSegmentCount; segmentCount++) {
                String segmentColor = getSegmentColor(segmentToDrawCount, segmentCount);
                paint.setColor(Color.parseColor(segmentColor));
                canvas.drawArc(oval, lastSweep, sweepAngleForSegment, true, paint);
                lastSweep = lastSweep + sweepAngleForSegment;
            }
        }

        private String getSegmentColor(int segmentToDrawCount, int segmentCount) {
            if (overridenStyle.overrideRadialColorIfOverbudget && amountSpent > totalAmount) return overridenStyle.radialColorForOverSpending;
            return (segmentCount > segmentToDrawCount) ? "#FFFFFF" : segmentColors[segmentCount];
        }

        private void prepareDimensions() {
            this.containerHeight = getHeight();
            this.containerWidth = getWidth();
            this.containerPaddingXCord = tenPercentOf(getWidth());
            this.containerPaddingYCord = tenPercentOf(getHeight());
            this.topSemiCircleBoundaryXCord = this.containerWidth - containerPaddingXCord;
            this.topSemiCircleBoundaryYCord = this.containerHeight - containerPaddingYCord;
            this.totalSegmentCount = segmentColors.length;
            this.sweepAngleForSegment = 360 / totalSegmentCount;
        }

        private int getSegmentToDrawCount() {
            int actualSweepAngle = (int) (360 * ((float) (amountSpent / totalAmount)));
            int segmentCount = (actualSweepAngle / sweepAngleForSegment);
            if (actualSweepAngle > 0 && (sweepAngleForSegment % actualSweepAngle) > 0) segmentCount++;
            return segmentCount;
        }

        private float tenPercentOf(int dimension) {
            return (TEN_PERCENT * dimension);
        }
    }

    private class OverlayCircle extends View {

        public static final float THIRTY_FIVE_PERCENT = .35f;

        public OverlayCircle(Context context) {
            super(context);
        }

        public OverlayCircle(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public OverlayCircle(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor(overridenStyle.centerColor));
            canvas.drawCircle(getHeight() / 2, getWidth() / 2, getRadius(), paint);
        }

        private float getRadius() {
            return overridenStyle.centerRadiusPercentageOfWidth * getWidth();
        }
    }

    public static class RadiatorStyle {

        public String centerColor;
        public String textColor;
        public float centerRadiusPercentageOfWidth;
        public boolean overrideRadialColorIfOverbudget = false;
        public String radialColorForOverSpending = "#f01614";

    }
}