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

public class GoalsWidget {

    private final Context context;
    private RelativeLayout parentContainer;
    private ColorBlocks blocks;
    private int height;
    private int width;

    public GoalsWidget(Context context, int height, int width) {
        this.context = context;
        this.height = height;
        this.width = width;
    }

    public RelativeLayout build() {
        parentContainer = new RelativeLayout(context);
        parentContainer.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        blocks = new ColorBlocks(context);
        parentContainer.addView(blocks);
        animate();
        return parentContainer;
    }

    public void animate() {
        Animation slideInAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in);
        blocks.startAnimation(slideInAnimation);
    }

    private class ColorBlocks extends View {

        private static final float ONE_PERCENT = 0.01f;
        private int containerHeight;
        private int containerWidth;
        private int blockHeight;
        private int blockWidth;
        private int eachBlockWidth;

        private String[] blockColors = new String[] {
            "#D7DF20",
            "#98CA3C",
            "#3AB549"
        };

        public ColorBlocks(Context context) {
            super(context);
            this.containerHeight = height;
            this.containerWidth = width;
        }

        public ColorBlocks(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public ColorBlocks(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            prepareDimensions();
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor(blockColors[0]));
            canvas.drawRect(0, 0, eachBlockWidth, blockHeight, paint);
            paint.setColor(Color.parseColor(blockColors[1]));
            canvas.drawRect(eachBlockWidth, 0, eachBlockWidth * 2, blockHeight, paint);
            paint.setColor(Color.parseColor(blockColors[2]));
            canvas.drawRect(eachBlockWidth * 2, 0, eachBlockWidth * 3, blockHeight, paint);

            super.onDraw(canvas);
        }

        private void prepareDimensions() {
            blockHeight = (int)(containerHeight - (containerHeight * ONE_PERCENT));
            blockWidth=(int)containerWidth/2;
            eachBlockWidth = blockWidth / blockColors.length;
        }
    }

}
