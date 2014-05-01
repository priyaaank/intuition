package com.poc.intuition.widgets;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.poc.intuition.R;

public class StackedExpensePredictor {

    private Context context;
    private Drawable barDrawable;
    private int daysInMonth;
    private int markedDay;
    private float maxAmount;
    private float spentAmount;
    private float predictedAmount;

    private int widthOfDayInBar;
    private int parentContainerWidth;

    private RelativeLayout baseContainer;
    private LinearLayout expenseBar;
    private LinearLayout predictionBar;
    private LinearLayout dayMarker;

    public StackedExpensePredictor(Context context, float maxAmount, float spentAmount, float predictedAmount, int daysInMonth, int markedDay, int parentContainerWidth) {
        this.context = context;
        this.maxAmount = maxAmount;
        this.spentAmount = spentAmount;
        this.predictedAmount = predictedAmount;
        this.parentContainerWidth = parentContainerWidth;
        this.daysInMonth = daysInMonth;
        this.markedDay = markedDay;
        this.widthOfDayInBar = parentContainerWidth / daysInMonth;
    }

    public RelativeLayout build() {
        initBaseContainer();
        initPredictionBar();
        initExpenseBar();
        initDayMarker();
        animate();
        return baseContainer;
    }

    private void initDayMarker() {
        int marginLeft = widthOfDayInBar * (markedDay);
        LinearLayout markerWrapper = new LinearLayout(context);
        LinearLayout.LayoutParams wrapperParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        markerWrapper.setLayoutParams(wrapperParams);
        dayMarker = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthOfDayInBar,LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(marginLeft, 0, 0, 0);
        dayMarker.setLayoutParams(params);
        dayMarker.setBackgroundColor(Color.BLACK);
        markerWrapper.addView(dayMarker);
        baseContainer.addView(markerWrapper);
    }

    public void animate() {
        Animation slideInAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in);
        Animation slideDownAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        predictionBar.startAnimation(slideInAnimation);
        expenseBar.startAnimation(slideInAnimation);
        dayMarker.startAnimation(slideDownAnimation);
    }

    private void initPredictionBar() {
        int predictionBarWidth = Math.round(parentContainerWidth * ratioOfPredictedSpendingToMaxExpenditure());
        predictionBar = new LinearLayout(context);
        predictionBar.setGravity(Gravity.LEFT);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(predictionBarWidth,LinearLayout.LayoutParams.MATCH_PARENT);
        predictionBar.setLayoutParams(params);
        predictionBar.setBackgroundColor(Color.GREEN);
        baseContainer.addView(predictionBar);
    }

    private float ratioOfPredictedSpendingToMaxExpenditure() {
        return predictedAmount/maxAmount;
    }

    private float ratioOfCurrentSpendingToMaxExpenditure() {
        return spentAmount/maxAmount;
    }

    private void initExpenseBar() {
        int expenseBarWidth = Math.round(parentContainerWidth * ratioOfCurrentSpendingToMaxExpenditure());
        expenseBar = new LinearLayout(context);
        expenseBar.setGravity(Gravity.LEFT);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(expenseBarWidth,LinearLayout.LayoutParams.MATCH_PARENT);
        expenseBar.setLayoutParams(params);
        expenseBar.setBackgroundColor(Color.WHITE);
        baseContainer.addView(expenseBar);
    }

    public void initBaseContainer() {
        baseContainer = new RelativeLayout(context);
        baseContainer.setGravity(Gravity.LEFT);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, unitInDPs(30));
        params.setMargins(unitInDPs(20), unitInDPs(20), unitInDPs(20), unitInDPs(20));
        baseContainer.setLayoutParams(params);
        baseContainer.setBackgroundColor(Color.parseColor("#71348D"));
    }

    private int unitInDPs(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }

}