package com.poc.intuition.widgets;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.poc.intuition.R;

public class Meter {

    private final Context context;
    private final LinearLayout widgetParentView;
    private int RADIAL_CIRCLE_VIEW_ID = 12421;
    private int CENTER_CIRCLE_VIEW_ID = 12422;
    private RelativeLayout parentView;
    private int radialDrawable;

    private Meter(Context context, LinearLayout widgetParent, int radialDrawable) {
        this.context = context;
        this.widgetParentView = widgetParent;
        this.radialDrawable = radialDrawable;
    }

    public static Meter greenWidget(Context context, LinearLayout widgetParent) {
        return new Meter(context, widgetParent, R.drawable.underspent);
    }

    public static Meter redWidget(Context context, LinearLayout widgetParent) {
        return new Meter(context, widgetParent, R.drawable.overspent);
    }

    public static Meter orangeWidget(Context context, LinearLayout widgetParent) {
        return new Meter(context, widgetParent, R.drawable.warning);
    }

    public Meter show() {
        setupWidget();
        addText();
        widgetParentView.addView(parentView);
        return this;
    }

    public void animate() {
        ImageView radialCircle = (ImageView) widgetParentView.findViewById(RADIAL_CIRCLE_VIEW_ID);
        Animation radialExpandAnimation = AnimationUtils.loadAnimation(context, R.anim.expand);
        radialCircle.startAnimation(radialExpandAnimation);
    }

    private void addText() {
        LinearLayout textLinearLayout = createLayoutWithHeightAndWidth(unitInDPs(500), unitInDPs(500));
        TextView amountTextView = new TextView(context);
        amountTextView.setText("You spent\n\n$200 out of $400");
        amountTextView.setGravity(Gravity.CENTER);
        amountTextView.setTextColor(Color.parseColor("#2c3e50"));
        textLinearLayout.addView(amountTextView);
        parentView.addView(textLinearLayout);
    }

    private void setupWidget() {
        setupParentView();
        LinearLayout centerCircleLinearLayout = createLayoutWithHeightAndWidth(unitInDPs(500), unitInDPs(500));
        LinearLayout radialCircleLinearLayout = createLayoutWithHeightAndWidth(unitInDPs(500), unitInDPs(500));
        ImageView centerCircle = getCircleWithAttributes(context.getResources().getDrawable(R.drawable.circle), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, CENTER_CIRCLE_VIEW_ID);
        ImageView radialCircle = getCircleWithAttributes(context.getResources().getDrawable(radialDrawable), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, RADIAL_CIRCLE_VIEW_ID);
        centerCircleLinearLayout.addView(centerCircle);
        radialCircleLinearLayout.addView(radialCircle);
        parentView.addView(radialCircleLinearLayout);
        parentView.addView(centerCircleLinearLayout);
    }

    private RelativeLayout setupParentView() {
        parentView = new RelativeLayout(context);
        parentView.setMinimumHeight(ActionBar.LayoutParams.MATCH_PARENT);
        parentView.setMinimumWidth(ActionBar.LayoutParams.MATCH_PARENT);
        parentView.setGravity(Gravity.CENTER);
        return parentView;
    }

    private ImageView getCircleWithAttributes(Drawable drawable, int maxHeight, int maxWidth, int id) {
        ImageView imageView = new ImageView(context);
        imageView.setImageDrawable(drawable);
        imageView.setMaxWidth(maxWidth);
        imageView.setMaxHeight(maxHeight);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setId(id);
        return imageView;
    }

    private LinearLayout createLayoutWithHeightAndWidth(int height, int width) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setMinimumWidth(width);
        linearLayout.setMinimumHeight(height);
        linearLayout.setGravity(Gravity.CENTER);
        return linearLayout;
    }

    private int unitInDPs(float value) {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }
}
