package com.poc.intuition.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import com.poc.intuition.R;

public class GradientBar {

    private RelativeLayout parentContainer;
    private GradientBlocks blocks;
    private final Double amountSpent;
    private final Double totalAmount;
    private float containerHeight;
    private float containerWidth;
    private Context context;

    public GradientBar(Context context, Double totalAmount, Double amountSpent, float height, float width) {
        this.context = context;
        this.totalAmount = totalAmount;
        this.amountSpent = amountSpent;
        this.containerHeight = height;
        this.containerWidth = width;
    }

    public RelativeLayout build() {
        parentContainer = new RelativeLayout(context);
        parentContainer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        blocks = new GradientBlocks(context);
        parentContainer.addView(blocks);
        animate();
        return parentContainer;
    }

    private void animate() {
        Animation slideInAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in);
        blocks.startAnimation(slideInAnimation);
    }

    private class GradientBlocks extends View {

        private static final float ONE_PERCENT = 0.01f;
        private String[] segmentColors = new String[]{
                "#FFCB06",
                "#FAA619",
                "#F58221",
                "#F15923",
                "#ED1C23"
        };

        private int height;
        private int width;
        private int eachSegmentWidth;
        private int segmentsToShow;

        public GradientBlocks(Context context) {
            super(context);
        }

        public GradientBlocks(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public GradientBlocks(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            prepareDimensions();
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            for(int segmentStartXCord = 0, colorIndex = 0; segmentStartXCord < (segmentsToShow * eachSegmentWidth) && colorIndex < segmentColors.length; segmentStartXCord = segmentStartXCord + eachSegmentWidth, colorIndex++ ) {
                paint.setColor(Color.parseColor(segmentColors[colorIndex]));
                canvas.drawRect(segmentStartXCord, 0, segmentStartXCord + eachSegmentWidth, height, paint);
            }
            super.onDraw(canvas);
        }

        private void prepareDimensions() {
            height = (int)(containerHeight - (containerHeight * ONE_PERCENT));
            width=(int)containerWidth;
            eachSegmentWidth = width / segmentColors.length-1;
            float percentOfTotalAmountSpent = (float)(amountSpent/totalAmount);
            segmentsToShow = (int)(segmentColors.length * percentOfTotalAmountSpent);
            if(segmentsToShow/segmentColors.length < percentOfTotalAmountSpent) segmentsToShow++;
        }
    }

}


